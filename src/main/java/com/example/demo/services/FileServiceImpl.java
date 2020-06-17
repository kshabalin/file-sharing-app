package com.example.demo.services;

import com.example.demo.entity.File;
import com.example.demo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Service
public class FileServiceImpl implements FileService {

    private UserService userService;

    private FileRepository repository;

    private LocalStorageService localStorageService;

    @Autowired
    public FileServiceImpl(UserService userService,
                           FileRepository repository,
                           LocalStorageService localStorageService) {
        this.userService = userService;
        this.repository = repository;
        this.localStorageService = localStorageService;
    }

    @Override
    @Transactional
    public File create(MultipartFile file, Long userId) throws Exception {
        File f = new File();
        f.setName(file.getOriginalFilename());
        f.setUserId(userId);
        File created = repository.save(f);
        localStorageService.saveFile(created.getId(), file.getOriginalFilename(), file.getBytes());
        return created;
    }

    @Override
    @Transactional
    public void share(File f, String userEmail) {
        Long userId = userService.findUserByEmail(userEmail).getId();
        repository.share(userId, f.getId());
    }
}
