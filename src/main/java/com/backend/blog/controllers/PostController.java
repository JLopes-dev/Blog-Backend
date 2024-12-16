package com.backend.blog.controllers;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.DTOs.DTOPostNotUser;
import com.backend.blog.models.Post;
import com.backend.blog.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DTOPost> createPost(@RequestBody @Valid DTOPost data, HttpServletRequest request)
    {
        Post postCreated = postService.createPostUsingJWT(data, request);
        return ResponseEntity.status(201).body(new DTOPost(postCreated.getId(), postCreated.getUser(), postCreated.getTitle(), postCreated.getDescription()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOPostNotUser>> findAllPosts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        return ResponseEntity.ok(postService.findAllPostsByUser(request, page, size)
                .stream()
                .map(post -> new DTOPostNotUser(post.getTitle(), post.getDescription())).toList());
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DTOPostNotUser> updatedPost(@RequestParam Long id, @RequestBody DTOPostNotUser data)
    {
        DTOPostNotUser newData = postService.updatedPostById(id, data);
        return ResponseEntity.ok(newData);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity deletePost(@RequestParam Long id, HttpServletRequest request)
    {
        postService.deletePostById(id, request);
        return ResponseEntity.noContent().build();
    }


}
