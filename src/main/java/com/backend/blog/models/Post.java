package com.backend.blog.models;

import com.backend.blog.DTOs.DTOPostNotUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "posts")
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    public void changeTitleOrDescription(DTOPostNotUser data)
    {
        if (data.title() != null)
        {
            this.title = data.title();
        }
        if (data.description() != null)
        {
            this.description = data.description();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Post(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }
}