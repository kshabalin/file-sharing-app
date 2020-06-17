package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedFileVO implements Serializable {
    private String email;
    private String fileId;
}
