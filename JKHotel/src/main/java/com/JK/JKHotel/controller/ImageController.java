package com.JK.JKHotel.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
public class ImageController {

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Try multiple possible paths
            Path[] possiblePaths = {
                Paths.get("uploads", filename),
                Paths.get(System.getProperty("user.dir"), "uploads", filename),
                Paths.get("JKHotel", "uploads", filename)
            };
            
            File file = null;
            for (Path path : possiblePaths) {
                File testFile = path.toFile();
                if (testFile.exists()) {
                    file = testFile;
                    System.out.println("Found image at: " + testFile.getAbsolutePath());
                    break;
                }
            }
            
            if (file == null) {
                System.out.println("Image not found: " + filename);
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new FileSystemResource(file);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                    .body(resource);
                    
        } catch (Exception e) {
            System.out.println("Error serving image: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
