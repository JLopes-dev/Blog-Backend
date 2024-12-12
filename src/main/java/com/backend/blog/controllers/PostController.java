package com.backend.blog.controllers;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.models.Post;
import com.backend.blog.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public ResponseEntity<Page<Post>> findAllPosts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(postService.findAllPostsByUser(request, page, size));
    }
}
