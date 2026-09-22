package com.techBloggingApp.Backend.Entity;

import com.techBloggingApp.Backend.Enum.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private final ERole role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
}
