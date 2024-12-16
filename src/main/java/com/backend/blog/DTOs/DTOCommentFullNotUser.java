package com.backend.blog.DTOs;

import com.backend.blog.models.Post;

public record DTOCommentFullNotUser(
        Long id,
        Post post,
        String description
) {
    public DTOCommentFullNotUser(Long id, Post post, String description) {
        this.id = id;
        this.post = post;
        this.description = description;
    }
}
