package com.aiingredient.service;

import com.aiingredient.model.AiConversation;
import com.aiingredient.model.AiGeneratedContent;
import com.aiingredient.repository.AiConversationRepository;
import com.aiingredient.repository.AiGeneratedContentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CozeAiService {
    @Value("${app.coze.api-key}") private String apiKey;
    @Value("${app.coze.api-url}") private String apiUrl;
    @Value("${app.coze.bot-id}") private String botId;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AiConversationRepository conversationRepository;
    private final AiGeneratedContentRepository contentRepository;
    
    public CozeAiService(AiConversationRepository conversationRepository,
                         AiGeneratedContentRepository contentRepository) {
        this.conversationRepository = conversationRepository;
        this.contentRepository = contentRepository;
    }
    
    public String chat(String query, Long userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            Map<String, Object> body = new HashMap<>();
            body.put("bot_id", botId);
            body.put("user_id", userId != null ? userId.toString() : "anonymous");
            body.put("query", query);
            body.put("stream", false);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                apiUrl + "/chat", entity, String.class);
            String aiResponse = parseCozeResponse(response.getBody());
            // Save conversation
            if (userId != null) {
                AiConversation conv = new AiConversation();
                conv.setUser(new com.aiingredient.model.User());
                conv.getUser().setId(userId);
                conv.setUserMessage(query);
                conv.setAiResponse(aiResponse);
                conv.setType("CHAT");
                conv.setCreatedAt(LocalDateTime.now());
                conversationRepository.save(conv);
            }
            return aiResponse;
        } catch (Exception e) {
            return "AI服务暂时不可用，请稍后重试。错误：" + e.getMessage();
        }
    }
    
    public String generateArticle(String topic, String type, Long userId) {
        String prompt = "";
        switch (type) {
            case "RECIPE": prompt = "请生成一份关于" + topic + "的专业菜谱文案，包含食材清单、步骤、小贴士。"; break;
            case "PURCHASE_PLAN": prompt = "请生成一份" + topic + "的食材采购计划表，包含品类、数量、预算建议。"; break;
            case "MENU": prompt = "请生成一份" + topic + "的食堂菜单方案。"; break;
            case "STORAGE": prompt = "请生成一份关于" + topic + "的食材存储科普短文。"; break;
            case "ANALYSIS": prompt = "请生成一份关于" + topic + "的食材损耗分析报告。"; break;
            default: prompt = "请生成关于" + topic + "的内容。";
        }
        String content = chat(prompt, userId);
        // Save generated content
        AiGeneratedContent gc = new AiGeneratedContent();
        gc.setUser(new com.aiingredient.model.User());
        gc.getUser().setId(userId);
        gc.setTitle(topic + " - " + getTypeName(type));
        gc.setContentType("ARTICLE");
        gc.setContent(content);
        gc.setCreatedAt(LocalDateTime.now());
        contentRepository.save(gc);
        return content;
    }
    
    public String generateImage(String prompt, Long userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            Map<String, Object> body = new HashMap<>();
            body.put("prompt", prompt);
            body.put("model", "coze-v1-image");
            body.put("size", "1024x1024");
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                apiUrl + "/images/generations", entity, String.class);
            String imageUrl = parseImageUrl(response.getBody());
            // Save
            AiGeneratedContent gc = new AiGeneratedContent();
            gc.setUser(new com.aiingredient.model.User());
            gc.getUser().setId(userId);
            gc.setTitle(prompt.length() > 50 ? prompt.substring(0, 50) : prompt);
            gc.setContentType("IMAGE");
            gc.setImageUrl(imageUrl);
            gc.setCreatedAt(LocalDateTime.now());
            contentRepository.save(gc);
            return imageUrl;
        } catch (Exception e) {
            return "AI绘图服务暂时不可用，错误：" + e.getMessage();
        }
    }
    
    public String analyzeData(String dataType, String dataContext, Long userId) {
        String prompt = "请对以下食材管理数据进行分析：\n数据类型：" + dataType + "\n数据内容：" + dataContext +
            "\n请提供详细的分析报告，包括趋势分析、问题诊断和优化建议。";
        String analysis = chat(prompt, userId);
        AiGeneratedContent gc = new AiGeneratedContent();
        gc.setUser(new com.aiingredient.model.User());
        gc.getUser().setId(userId);
        gc.setTitle(dataType + "分析报告");
        gc.setContentType("ANALYSIS");
        gc.setContent(analysis);
        gc.setCreatedAt(LocalDateTime.now());
        contentRepository.save(gc);
        return analysis;
    }
    
    private String parseCozeResponse(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            return root.path("messages").get(0).path("content").asText();
        } catch (Exception e) {
            return json;
        }
    }
    
    private String parseImageUrl(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            return root.path("data").get(0).path("url").asText();
        } catch (Exception e) {
            return "";
        }
    }
    
    private String getTypeName(String type) {
        Map<String, String> map = Map.of(
            "RECIPE", "菜谱文案", "PURCHASE_PLAN", "采购计划",
            "MENU", "菜单方案", "STORAGE", "存储科普", "ANALYSIS", "分析报告"
        );
        return map.getOrDefault(type, type);
    }
    
    public List<AiConversation> getConversations(Long userId) {
        return conversationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public List<AiGeneratedContent> getGeneratedContents(Long userId, String contentType) {
        if (contentType != null && !contentType.isEmpty())
            return contentRepository.findByUserIdAndContentTypeOrderByCreatedAtDesc(userId, contentType);
        return contentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public void toggleFavorite(Long contentId) {
        AiGeneratedContent gc = contentRepository.findById(contentId).orElseThrow();
        gc.setFavorite(!gc.getFavorite());
        contentRepository.save(gc);
    }
    
    public void deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
    }
}
