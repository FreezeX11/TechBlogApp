package com.techBloggingApp.Backend.Entity;

import com.techBloggingApp.Backend.Enum.ReportStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
