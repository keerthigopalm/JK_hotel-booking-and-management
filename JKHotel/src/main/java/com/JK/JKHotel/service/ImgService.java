package com.JK.JKHotel.service;

import com.JK.JKHotel.exception.OurException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImgService {
    private static final String UPLOAD_DIR = "uploads"; // project-root/uploads

    public static String saveImageToUpload(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = file.getOriginalFilename();
            String filename = System.currentTimeMillis() + "_" + originalName;
            Path filePath = uploadPath.resolve(filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return UPLOAD_DIR + "/" + filename;
        } catch (IOException e) {
            throw new OurException("Failed to save image locally: " + e.getMessage());
        }
    }
}
