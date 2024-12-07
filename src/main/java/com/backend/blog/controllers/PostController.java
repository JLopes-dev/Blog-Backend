package com.backend.blog.controllers;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DTOPost> createPost(@RequestBody DTOPost data, HttpServletRequest request)
    {
        DTOPost postCreated = postService.createPostUsingJWT(data, request);
        return ResponseEntity.status(201).body(postCreated);
    }

}
