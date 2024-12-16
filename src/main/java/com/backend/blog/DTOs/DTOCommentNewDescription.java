package com.backend.blog.DTOs;

import jakarta.validation.constraints.NotBlank;

public record DTOCommentNewDescription(
        @NotBlank
        String description
) {
}
