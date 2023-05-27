package com.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.file.FileDownload;
import com.project.file.FileResponse;
import com.project.file.FileUpload;
import com.project.model.User;
import com.project.response.Responsehandler;
import com.project.service.UserService;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	// build create User REST API
	@PostMapping("/signup")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = userService.createUser(user);
		return Responsehandler.generatedResponse("User Created Successfully", HttpStatus.CREATED, savedUser);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody User user) {

		
		User user1 = userService.loginUser(user);

//		System.out.println("Inside Login");
		if (user1.getEmail() == null) {
//			System.out.println("Inside in if");
			return Responsehandler.generatedResponse("Unable to Login, User Not Registered", HttpStatus.UNAUTHORIZED, null, true);
		} else {
//			System.out.println("Inside in else");
			return Responsehandler.generatedResponse("User Login Successfully", HttpStatus.OK, user1);
			
		}
	}

	// build get user by id REST API
	// http://localhost:8080/api/users/1
	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Build Get All Users REST API
	// http://localhost:8080/api/users
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Build Update User REST API
	@PutMapping("{id}")
	// http://localhost:8080/api/users/1
	public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
		user.setId(userId);
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	// Build Delete User REST API
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAllUser();
		return new ResponseEntity<>("All Users Are Deleted!!", HttpStatus.OK);
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile)
			throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();

		String filecode = FileUpload.saveFile(fileName, multipartFile);
		System.out.println("fileCode => " + filecode);

		FileResponse response = new FileResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/downloadFile/" + filecode);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/downloadFile/{fileCode}")
	public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
		FileDownload downloadUtil = new FileDownload();

		Resource resource = null;

		try {
			resource = downloadUtil.getFileAsResource(fileCode);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}

		if (resource == null) {
			return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
		}

		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
	}

	@GetMapping("/displayImage/{fileCode}")
	public ResponseEntity<?> displayFile(@PathVariable("fileCode") String fileCode) {
		FileDownload download = new FileDownload();

		Resource resource = null;

		try {
			resource = download.getFileAsResource(fileCode);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

		if (resource == null) {
			return new ResponseEntity<>("File Not found", HttpStatus.NOT_FOUND);
		}

		String extension = "jpg";
		String fileName = resource.getFilename();
		int index = fileName.lastIndexOf('.');
		if (index > 0) {
			extension = fileName.substring(index + 1);
		}

		return ResponseEntity.ok()
				.contentType(MediaType
						.parseMediaType(extension == "jpg" ? MediaType.IMAGE_JPEG_VALUE : MediaType.IMAGE_PNG_VALUE))
				.body(resource);
	}

	@PostMapping("/addUser")
	public ResponseEntity<Object> addUser(@ModelAttribute User user, @RequestParam("file") MultipartFile multipartFile)
			throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//remove extention of file name
		// if (fileName.indexOf(".") > 0)
//    			    fileName = fileName.substring(0, fileName.lastIndexOf("."));
		String filecode = FileUpload.saveFile(fileName, multipartFile);
//    		 System.out.println("file received => " + fileName);
		user.setPhoto(filecode);
		User user1 = userService.createUser(user);
		return Responsehandler.generatedResponse("User Created Successfully", HttpStatus.CREATED, user1);
	}

}
