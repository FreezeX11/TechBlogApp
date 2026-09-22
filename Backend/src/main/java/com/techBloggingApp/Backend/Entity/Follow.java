package com.techBloggingApp.Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "followed_id"),
                @UniqueConstraint(columnNames = "follower_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean receiveNotification;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;
}
