package com.example.demo.testUtils;

import com.example.demo.entity.Authority;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
public class AuthorityUtils {
    public static Authority createAuthority(String userName, String authority) {
        return new Authority(null, userName, authority);
    }
}
