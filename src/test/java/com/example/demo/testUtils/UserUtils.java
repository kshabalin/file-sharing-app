package com.example.demo.testUtils;

import com.example.demo.entity.User;

import java.util.ArrayList;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
public class UserUtils {
    public static User createUser(String email) {
        User u = new User();
        u.setEmail(email);
        u.setPassword("password");
        u.setEnabled(true);
        return u;
    }
}
