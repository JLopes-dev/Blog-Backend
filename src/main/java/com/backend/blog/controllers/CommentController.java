package com.backend.blog.controllers;


import com.backend.blog.DTOs.DTOComment;
import com.backend.blog.models.Comment;
import com.backend.blog.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @Transactional
    public ResponseEntity<Comment> createNewComment(@RequestBody DTOComment data, HttpServletRequest request)
    {
        Comment comment = commentService.createCommentService(request, data);
        return ResponseEntity.status(201).body(new Comment(comment.getId(),comment.getUser(), comment.getPost(), comment.getDescription()));
    }

}
