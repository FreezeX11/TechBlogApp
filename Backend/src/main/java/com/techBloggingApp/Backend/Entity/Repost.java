package com.techBloggingApp.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "reposts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "viewer_id"),
                @UniqueConstraint(columnNames = "blog_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

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
