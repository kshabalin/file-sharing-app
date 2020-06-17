package com.example.demo.mappers;

import com.example.demo.vo.FileVO;
import com.example.demo.vo.FilesVO;
import com.example.demo.entity.File;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Component
public class FileMapper {

    public FileVO toVOLite(File f) {
        FileVO vo = new FileVO();
        vo.setId(f.getId().toString());
        return vo;
    }

    public FileVO toVO(File f) {
        FileVO vo = toVOLite(f);
        vo.setName(f.getName());
        return vo;
    }

    public FilesVO toVO(List<File> owned, List<File> shared) {
        FilesVO vo = new FilesVO();
        vo.setOwned(owned.stream().map(this::toVO).collect(Collectors.toList()));
        vo.setShared(shared.stream().map(this::toVO).collect(Collectors.toList()));
        return vo;
    }
}
