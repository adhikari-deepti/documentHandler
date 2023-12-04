package com.document.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	public final String UPLOAD_DIR = new ClassPathResource("static/files/").getFile().getAbsolutePath();

	public FileUploadHelper() throws IOException {

	}

	public boolean uploadFile(MultipartFile MFile) {
		boolean f = false;
		try {

			Files.copy(MFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + MFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			f = true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return f;
	}
}
