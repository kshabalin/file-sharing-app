package com.example.demo.services;

import com.example.demo.entity.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
public interface FileService {

    File create(MultipartFile file, Long currentUserId) throws Exception;

    void share(File f, String userEmail);
}
