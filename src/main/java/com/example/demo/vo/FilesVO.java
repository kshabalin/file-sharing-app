package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilesVO implements Serializable {
    private List<FileVO> owned;
    private List<FileVO> shared;
}
