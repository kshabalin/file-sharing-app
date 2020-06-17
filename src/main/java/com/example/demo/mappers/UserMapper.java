package com.example.demo.mappers;

import com.example.demo.vo.UserVO;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Component
public class UserMapper {
    public User toEntity(UserVO vo) {
        User u = new User();
        u.setEmail(vo.getEmail());
        u.setPassword(vo.getPassword());
        return u;
    }
}
