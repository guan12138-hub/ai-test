package com.aiingredient.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ai_conversations")
public class AiConversation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(columnDefinition = "TEXT")
    private String userMessage;
    @Column(columnDefinition = "TEXT")
    private String aiResponse;
    @Column(length = 50)
    private String type; // CHAT, ARTICLE, IMAGE, ANALYSIS
    @Column(length = 200)
    private String title;
    private LocalDateTime createdAt = LocalDateTime.now();
}
