package com.document.helper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileDownloadHelper {

//	
	private final ResourceLoader resourceLoader;

	@Autowired
	public FileDownloadHelper(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource loadFileAsResource(String filename) throws Exception {
		try {
			Path filePath = getResourceFilePath(filename);
			Resource resource = resourceLoader.getResource("file:" + filePath.toString());

			if (resource.exists()) {
				return resource;
			} else {
				throw new Exception("File not found: " + filename);
			}
		} catch (IOException e) {
			throw new IOException("Error loading file: " + filename, e);
		}
	}

	private Path getResourceFilePath(String filename) throws IOException {
		String uploadDir = resourceLoader.getResource("classpath:static/files/").getFile().getAbsolutePath();
		return Paths.get(uploadDir).resolve(filename).normalize();
	}
}
