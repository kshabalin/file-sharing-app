package com.example.demo.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalStorageServiceIntegrationTest {

    @Autowired
    private LocalStorageService localStorageService;

    @Test
    public void whenValidFile_thenUserShouldBeSaved() throws Exception {
        String fileName = "file.txt";
        Long fileId = 1L;
        byte[] bytes = "contents".getBytes();

        localStorageService.saveFile(fileId, fileName, bytes);
        ByteArrayResource resource = localStorageService.getFile(fileId, fileName);

        assertThat(resource).isNotEqualTo(null);
        Assert.assertArrayEquals(resource.getByteArray(), bytes);
    }

    @Test
    public void whenFileNotExists_thenExceptionThrown() {
        assertThrows(Exception.class, () -> {
            localStorageService.getFile(2L, "file.txt");
        });
    }

    @Test
    public void whenFileIsInvalid_thenExceptionThrown() {
        assertThrows(Exception.class, () -> {
            localStorageService.saveFile(1L, "file.txt", null);
        });
    }
}
