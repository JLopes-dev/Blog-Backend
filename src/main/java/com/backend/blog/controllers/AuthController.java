package com.backend.blog.controllers;

import com.backend.blog.DTOs.DTOUser;
import com.backend.blog.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<DTOUser> createNewUserInDatabase(@RequestBody DTOUser user)
    {
        return ResponseEntity.status(201).body(authService.createUser(user));
    }


}
