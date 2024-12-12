package com.backend.blog.services;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.models.Post;
import com.backend.blog.models.User;
import com.backend.blog.repositories.PostRepository;
import com.backend.blog.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    public DTOPost createPostUsingJWT(DTOPost data, HttpServletRequest request)
    {
        User user = userService.findUserByJWTToken(request);
        Post post = postRepository.save(new Post(user, data.title(), data.description()));
        return new DTOPost(post.getUser(), post.getTitle(), post.getDescription());
    }


    public Page<Post> findAllPostsByUser(HttpServletRequest request, int page, int size)
    {
        User user = userService.findUserByJWTToken(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());
        return postRepository.findByUserId(user.getId(), pageable);
    }

}
