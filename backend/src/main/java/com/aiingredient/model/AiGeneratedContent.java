package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ai_generated_contents")
public class AiGeneratedContent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(length = 100)
    private String title;
    @Column(length = 50)
    private String contentType; // ARTICLE, IMAGE, ANALYSIS
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(length = 500)
    private String imageUrl;
    @Column(length = 500)
    private String remark;
    private Boolean favorite = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
