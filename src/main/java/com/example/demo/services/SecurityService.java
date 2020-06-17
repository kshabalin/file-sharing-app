package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Service
public class SecurityService {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public SecurityService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public void destroySession() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public User getCurrentUser() {
        return userService.findUserByEmail(getCurrentUsername());
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public void authenticate(String userName, String password) throws AuthenticationException {
        Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
        auth = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
