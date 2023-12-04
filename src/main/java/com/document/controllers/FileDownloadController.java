package com.document.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.helper.FileDownloadHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileDownloadController {

	private final FileDownloadHelper fileDownloadHelper;

	@Autowired
	public FileDownloadController(FileDownloadHelper fileDownloadHelper) {
		this.fileDownloadHelper = fileDownloadHelper;
	}

	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
		try {
			// Load file as Resource
			Resource resource = fileDownloadHelper.loadFileAsResource(filename);

			// Set Content-Disposition header for the browser to prompt download
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.contentType(MediaType.APPLICATION_PDF).body(resource);
		} catch (FileNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}
	}
}
