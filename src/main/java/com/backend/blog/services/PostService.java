package com.backend.blog.services;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.models.Post;
import com.backend.blog.models.User;
import com.backend.blog.repositories.PostRepository;
import com.backend.blog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;

    public DTOPost createPostUsingJWT(DTOPost data, HttpServletRequest request)
    {
        String token = jwtService.getHeader(request);
        String getUsername = jwtService.verifyToken(token);
        User user = userRepository.findByUsername(getUsername);
        Post post = postRepository.save(new Post(user, data.title(), data.description()));
        return new DTOPost(post.getUser(), post.getTitle(), post.getDescription());
    }

}
