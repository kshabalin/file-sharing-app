package com.example.demo.controllers;

import com.example.demo.vo.FileVO;
import com.example.demo.vo.FilesVO;
import com.example.demo.vo.SharedFileVO;
import com.example.demo.entity.File;
import com.example.demo.entity.User;
import com.example.demo.mappers.FileMapper;
import com.example.demo.services.LocalStorageService;
import com.example.demo.services.SecurityService;
import com.example.demo.services.FileService;
import com.example.demo.utils.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@RestController
@RequestMapping("/api")
public class FileController {

    private FileService fileService;

    private FileMapper mapper;

    private LocalStorageService storageService;

    private SecurityService securityService;

    public FileController(FileService fileService,
                          FileMapper mapper,
                          LocalStorageService storageService,
                          SecurityService securityService) {
        this.fileService = fileService;
        this.mapper = mapper;
        this.storageService = storageService;
        this.securityService = securityService;
    }

    @GetMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public FilesVO get() {
        User user = securityService.getCurrentUser();
        return mapper.toVO(user.getOwnedFiles(), user.getSharedFiles());
    }

    @GetMapping(path = "/file/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        try {
            File f = findByCurrentUser(id);
            if (f != null) {
                Resource resource = storageService.getFile(f.getId(), f.getName());
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/file",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileVO> upload(@RequestParam MultipartFile file) {
        try {
            File created = fileService.create(file, securityService.getCurrentUserId());
            return new ResponseEntity<>(mapper.toVOLite(created), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }

    }

    @PostMapping(path = "/share", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity share(@RequestBody SharedFileVO sharedFile) {
        try {
            File f = findOwnedByCurrentUser(Long.parseLong(sharedFile.getFileId()));
            if (f != null) {
                fileService.share(f, sharedFile.getEmail());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    private File findByCurrentUser(Long id) {
        User u = securityService.getCurrentUser();
        File f = FileUtils.findByUserId(u.getOwnedFiles(), id);
        return f != null ? f : FileUtils.findByUserId(u.getSharedFiles(), id);
    }

    private File findOwnedByCurrentUser(Long id) {
        User u = securityService.getCurrentUser();
        return FileUtils.findByUserId(u.getOwnedFiles(), id);
    }
}
