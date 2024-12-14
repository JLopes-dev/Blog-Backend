package com.backend.blog.services;

import com.backend.blog.DTOs.DTOComment;
import com.backend.blog.models.Comment;
import com.backend.blog.models.Post;
import com.backend.blog.models.User;
import com.backend.blog.repositories.CommentRepository;
import com.backend.blog.repositories.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void deleteCommentService(Long id)
    {
        commentRepository.deleteById(id);
    }

}
