package com.aiingredient.repository;

import com.aiingredient.model.AiConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiConversationRepository extends JpaRepository<AiConversation, Long> {
    List<AiConversation> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AiConversation> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type);
}
