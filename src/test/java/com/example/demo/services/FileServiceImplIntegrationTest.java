package com.example.demo.services;

import com.example.demo.entity.File;
import com.example.demo.repository.FileRepository;
import com.example.demo.testUtils.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplIntegrationTest {

    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private LocalStorageService localStorageService;

    @Autowired
    private FileService fileService;

    @Test
    public void whenValidFile_thenShouldBeSaved() throws Exception {
        File file = FileUtils.createFile("file.txt", 1L);
        Mockito.when(fileRepository.save(Mockito.any(File.class))).thenReturn(file);

        MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), new byte[]{});
        File created = fileService.create(multipartFile, 1L);

        assertThat(created.getName()).isEqualTo(file.getName());
        assertThat(created.getUserId()).isEqualTo(file.getUserId());
        assertThat(created.getId()).isEqualTo(file.getId());
    }
}
