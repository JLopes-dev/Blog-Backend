package com.backend.blog.DTOs;

import com.backend.blog.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOPost(
        User user,
        @NotNull
        String title,
        @NotNull
        String description
) {}
