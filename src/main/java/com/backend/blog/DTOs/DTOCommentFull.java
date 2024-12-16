package com.backend.blog.DTOs;

import com.backend.blog.models.Post;
import com.backend.blog.models.User;

public record DTOCommentFull(
        Long id,
        User user,
        Post post,
        String description
) {
}
