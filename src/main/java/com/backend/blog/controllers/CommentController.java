package com.backend.blog.controllers;


import com.backend.blog.DTOs.DTOComment;
import com.backend.blog.DTOs.DTOCommentFull;
import com.backend.blog.DTOs.DTOCommentFullNotUser;
import com.backend.blog.DTOs.DTOCommentNewDescription;
import com.backend.blog.models.Comment;
import com.backend.blog.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<DTOCommentFull> createNewComment(@RequestBody DTOComment data, HttpServletRequest request)
    {
        Comment comment = commentService.createCommentService(request, data);
        return ResponseEntity.status(201).body(new DTOCommentFull(comment.getId(), comment.getUser(), comment.getPost(), comment.getDescription()));
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity deleteComment(@RequestParam Long id, HttpServletRequest request)
    {
        commentService.deleteCommentService(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DTOCommentFull> updateComment(@RequestParam Long id, @RequestBody DTOCommentNewDescription data, HttpServletRequest request)
    {
        Comment commentUpdated = commentService.updateCommentService(id, data, request);
        return ResponseEntity.status(201).body(new DTOCommentFull(commentUpdated.getId(), commentUpdated.getUser(), commentUpdated.getPost(), commentUpdated.getDescription()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOCommentFullNotUser>> showAllComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        return ResponseEntity.ok(commentService.showAllCommentsByUserId(request, page, size).stream().map(comment -> new DTOCommentFullNotUser(comment.getId(), comment.getPost(), comment.getDescription())).toList());
    }
}
