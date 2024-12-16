package com.backend.blog.services;

import com.backend.blog.DTOs.DTOComment;
import com.backend.blog.DTOs.DTOCommentNewDescription;
import com.backend.blog.models.Comment;
import com.backend.blog.models.Post;
import com.backend.blog.models.User;
import com.backend.blog.repositories.CommentRepository;
import com.backend.blog.repositories.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;

    public Comment createCommentService(HttpServletRequest request, DTOComment commentData)
    {
        User user = userService.findUserByJWTToken(request);
        Optional<Post> post = postRepository.findById(commentData.postId());
        if (post.isPresent())
        {
            return commentRepository.save(new Comment(user, post.get(), commentData.description()));
        }

        throw new RuntimeException("Post não Encontrado");
    }

    public void deleteCommentService(Long id, HttpServletRequest request)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent())
        {
            if (comment.get().getUser().getUsername().equals(userService.findUserByJWTToken(request).getUsername()))
            {
                commentRepository.deleteById(id);
            }
        }
        else
        {
            throw new RuntimeException("Sem Autorização ou ID inválido");
        }
    }

    public Comment updateCommentService(Long id, DTOCommentNewDescription data, HttpServletRequest request)
    {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent())
        {
            if (comment.get().getUser().getUsername().equals(userService.findUserByJWTToken(request).getUsername()))
            {
                comment.get().updateDescription(data.description());
                return new Comment(comment.get().getId(), comment.get().getUser(), comment.get().getPost(), comment.get().getDescription());
            }
        }

        throw new RuntimeException("Sem Autorização ou ID inválido");
    }

    public Page<Comment> showAllCommentsByUserId(HttpServletRequest request, int page, int size)
    {
        User user = userService.findUserByJWTToken(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("description").ascending());
        return commentRepository.findByUserId(user.getId(), pageable);
    }

}
