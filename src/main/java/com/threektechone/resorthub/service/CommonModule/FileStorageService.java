package com.threektechone.resorthub.service.CommonModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path baseDir;

    public FileStorageService(@Value("${file.storage-dir}") String baseDir) throws IOException {
        this.baseDir = Paths.get(baseDir);
        if (!Files.exists(this.baseDir)) Files.createDirectories(this.baseDir);
    }

    public String storeFile(MultipartFile file, String folder) throws IOException {
        Path dirPath = baseDir.resolve(folder);
        if (!Files.exists(dirPath)) Files.createDirectories(dirPath);

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetPath = dirPath.resolve(filename);
        Files.write(targetPath, file.getBytes());

        return targetPath.toString();
    }
}
