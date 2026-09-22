package com.techBloggingApp.Backend.Entity;

import com.techBloggingApp.Backend.Enum.CommentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String content;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
}
