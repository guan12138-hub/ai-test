package com.aiingredient.repository;

import com.aiingredient.model.AiGeneratedContent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiGeneratedContentRepository extends JpaRepository<AiGeneratedContent, Long> {
    List<AiGeneratedContent> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AiGeneratedContent> findByUserIdAndContentTypeOrderByCreatedAtDesc(Long userId, String contentType);
    List<AiGeneratedContent> findByUserIdAndFavoriteTrueOrderByCreatedAtDesc(Long userId);
}
