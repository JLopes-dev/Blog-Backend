package com.backend.blog.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOComment(
        @NotNull
        Long postId,
        @NotBlank
        String description
) {
}
