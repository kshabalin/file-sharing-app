package com.example.demo.testUtils;

import com.example.demo.entity.File;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
public class FileUtils {
    public static File createFile(String fileName, Long userId) {
        return new File(null, fileName, userId);
    }
}
