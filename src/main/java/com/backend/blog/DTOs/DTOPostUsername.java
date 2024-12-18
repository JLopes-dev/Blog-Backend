package com.backend.blog.DTOs;

public record DTOPostUsername(
        Long id,
        String username,
        String title,
        String description
) {
}
