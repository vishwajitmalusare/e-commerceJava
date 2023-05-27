package com.project.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	public static String saveFile(String fileName, MultipartFile multipartFile)
		throws IOException {
		Path uploadPath = Paths.get("Files-Upload");
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
//		String fileCode = RandomStringUtils.randomAlphanumeric(8);
		LocalDateTime localDate = LocalDateTime.now();
		String fileCode = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss").format(localDate);
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileCode + "-"+fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could nto save file: "+fileName, ioe);
		}
		return fileCode;
	}
}
