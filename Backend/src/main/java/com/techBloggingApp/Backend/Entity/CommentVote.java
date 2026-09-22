package com.techBloggingApp.Backend.Entity;

import com.techBloggingApp.Backend.Enum.VoteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
}
