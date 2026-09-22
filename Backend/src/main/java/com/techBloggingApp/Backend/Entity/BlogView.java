package com.techBloggingApp.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_views")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "viewer_id")
    private User viewer;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
