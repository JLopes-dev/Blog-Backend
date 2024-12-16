package com.backend.blog.DTOs;

public record DTOPostNotUser(Long id, String title, String description) {
    public DTOPostNotUser(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
