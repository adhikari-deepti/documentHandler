package com.document.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.document.helper.FileUploadHelper;

@RestController
public class FileUploadController {
	@Autowired
	private FileUploadHelper fileUploadHelper;

	@PostMapping("/upload-file")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

		try {
			// validation
			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Resquest must contain file");
			}
			if (!file.getContentType().equals("application/pdf")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("only pdf document are allowed");
			}
			// file upload code..
			boolean f = fileUploadHelper.uploadFile(file);
			if (f) {

				return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
						.path(file.getOriginalFilename()).toUriString());
			}
			;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("some thing went wrong");
	}
}
