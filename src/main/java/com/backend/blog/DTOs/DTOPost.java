package com.backend.blog.DTOs;

import com.backend.blog.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOPost(
        @NotNull
        User user,
        @NotBlank
        String title,
        @NotBlank
        String description
) {
        public DTOPost(String title, String description) {
                this(null, title, description);
        }
}
