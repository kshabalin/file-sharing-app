package com.example.demo.services;

import com.example.demo.entity.User;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
public interface UserService {
    User findUserByEmail(String email);
    User saveUser(User user);
}
