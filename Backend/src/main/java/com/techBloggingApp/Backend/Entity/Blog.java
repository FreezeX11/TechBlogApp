package com.techBloggingApp.Backend.Entity;

import com.techBloggingApp.Backend.Enum.BlogStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SQLDelete(sql = "UPDATE blogs SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String coverUrl;

    private String content;

    @Enumerated(EnumType.STRING)
    private BlogStatus blogStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private Set<Media> media = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "blog_tags",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
