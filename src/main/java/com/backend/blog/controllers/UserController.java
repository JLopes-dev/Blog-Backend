package com.backend.blog.controllers;

import com.backend.blog.DTOs.DTOUser;
import com.backend.blog.DTOs.DTOUserUpdated;
import com.backend.blog.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change/password")
    @Transactional
    public ResponseEntity<DTOUser> updatePasswordUsingUsername(@RequestBody DTOUserUpdated data, HttpServletRequest request)
    {
        userService.changePasswordByTokenJWT(data, request);
        return ResponseEntity.noContent().build();
    }

}
