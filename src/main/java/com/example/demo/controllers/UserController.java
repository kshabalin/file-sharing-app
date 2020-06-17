package com.example.demo.controllers;

import com.example.demo.vo.UserVO;
import com.example.demo.entity.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.services.SecurityService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Controller
public class UserController {

    private UserService userService;
    private UserMapper mapper;
    private SecurityService securityService;

    @Autowired
    public UserController(UserService userService, UserMapper mapper, SecurityService securityService) {
        this.userService = userService;
        this.mapper = mapper;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserVO dto) {
        String password = dto.getPassword();
        User user = mapper.toEntity(dto);
        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser == null) {
            userService.saveUser(user);
        }
        securityService.destroySession();
        securityService.authenticate(user.getEmail(), password);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
