package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Service
public class LocalStorageService {

    @Value("${uploads.directory}")
    private String storagePath;

    void saveFile(Long id, String fileName, byte[] data) {
        try {
            String fileId = id.toString();
            String fullPath = getFileFullPath(fileId, fileName);
            createFileFolder(fileId);
            File f = new File(fullPath);
            FileOutputStream output = new FileOutputStream(f);
            output.write(data);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteArrayResource getFile(Long id, String name) throws Exception {
        Path path = Paths.get(getFileFullPath(id.toString(), name));
        return new ByteArrayResource(Files.readAllBytes(path));
    }

    private void createFileFolder(String fileId) {
        new File(getFileFolderPath(fileId)).mkdir();
    }

    private String getFileFullPath(String fileId, String fileName) {
        return String.format("%s/%s/%s", storagePath, fileId, fileName);
    }

    private String getFileFolderPath(String fileId) {
        return String.format("%s/%s", storagePath, fileId);
    }
}
