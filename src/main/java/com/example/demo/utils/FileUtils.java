package com.example.demo.utils;

import com.example.demo.entity.File;

import java.util.List;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
public class FileUtils {

    public static File findByUserId(List<File> files, Long id) {
        return files.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }
}
