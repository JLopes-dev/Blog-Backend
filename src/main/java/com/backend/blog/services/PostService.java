package com.backend.blog.services;

import com.backend.blog.DTOs.DTOPost;
import com.backend.blog.DTOs.DTOPostNotUser;
import com.backend.blog.models.Post;
import com.backend.blog.models.User;
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
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    public Post createPostUsingJWT(DTOPost data, HttpServletRequest request)
    {
        User user = userService.findUserByJWTToken(request);
        Post post = postRepository.save(new Post(user, data.title(), data.description()));
        return new Post(post.getId(), post.getUser(), post.getTitle(), post.getDescription());
    }


    public Page<Post> findAllPostsByUser(HttpServletRequest request, int page, int size)
    {
        User user = userService.findUserByJWTToken(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return postRepository.findByUserId(user.getId(), pageable);
    }

    public DTOPostNotUser updatedPostById(Long id, DTOPostNotUser data)
    {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent())
        {
            post.get().changeTitleOrDescription(data);
            return new DTOPostNotUser(post.get().getId(), post.get().getTitle(), post.get().getDescription());
        }
        else { throw new RuntimeException("Houve um erro ao tentar encontrar o registro"); }
    }
     public void deletePostById(Long id, HttpServletRequest request)
     {
         Optional<Post> post = postRepository.findById(id);
         if (post.isPresent())
         {
             if (post.get().getUser().getUsername().equals(userService.findUserByJWTToken(request).getUsername()))
             {
                 postRepository.deleteById(post.get().getId());
             }
         }
         else {
             throw new RuntimeException("Sem Autorização ou ID inválido");
         }
     }
}
