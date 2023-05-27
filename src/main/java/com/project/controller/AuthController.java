package com.project.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.ApiException;
import com.project.file.FileDownload;
import com.project.model.User;
import com.project.response.JwtAuthRequest;
import com.project.response.JwtAuthResponse;
import com.project.security.JwtTokenHelper;
import com.project.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
//		System.out.println("loading username,,,");
//		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, User.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	
	}

	private void authenticate(String username, String password) throws ApiException, Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		
		try {
			
		authManager.authenticate(authenticationToken);
		

		} catch (BadCredentialsException e) {
			System.err.println(e);
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@Valid @ModelAttribute User user) {
		user.setUsername(user.getEmail());
		
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}
	
	@GetMapping("displayImage/{fileCode}")
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
//	// get loggedin user data
//	@Autowired
//	private UserRepo userRepo;
//	@Autowired
//	private ModelMapper mapper;
//
//	@GetMapping("/current-user/")
//	public ResponseEntity<UserDto> getUser(Principal principal) {
//		User user = this.userRepo.findByEmail(principal.getName()).get();
//		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
//	}

}
