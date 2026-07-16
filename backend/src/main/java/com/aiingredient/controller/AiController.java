package com.aiingredient.controller;

import com.aiingredient.dto.ApiResponse;
import com.aiingredient.dto.CozeRequest;
import com.aiingredient.model.AiConversation;
import com.aiingredient.model.AiGeneratedContent;
import com.aiingredient.service.CozeAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final CozeAiService cozeAiService;
    public AiController(CozeAiService cozeAiService) { this.cozeAiService = cozeAiService; }
    
    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<Map<String, String>>> chat(@RequestBody CozeRequest request, @RequestAttribute Long userId) {
        String response = cozeAiService.chat(request.getQuery(), userId);
        Map<String, String> result = new HashMap<>();
        result.put("response", response);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @PostMapping("/article")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateArticle(
            @RequestParam String topic, @RequestParam String type, @RequestAttribute Long userId) {
        String content = cozeAiService.generateArticle(topic, type, userId);
        Map<String, String> result = new HashMap<>();
        result.put("content", content);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @PostMapping("/image")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateImage(
            @RequestParam String prompt, @RequestAttribute Long userId) {
        String imageUrl = cozeAiService.generateImage(prompt, userId);
        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", imageUrl);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<Map<String, String>>> analyze(
            @RequestParam String dataType, @RequestParam String dataContext, @RequestAttribute Long userId) {
        String analysis = cozeAiService.analyzeData(dataType, dataContext, userId);
        Map<String, String> result = new HashMap<>();
        result.put("analysis", analysis);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @GetMapping("/conversations")
    public ResponseEntity<ApiResponse<List<AiConversation>>> getConversations(@RequestAttribute Long userId) {
        return ResponseEntity.ok(ApiResponse.success(cozeAiService.getConversations(userId)));
    }
    
    @GetMapping("/contents")
    public ResponseEntity<ApiResponse<List<AiGeneratedContent>>> getContents(
            @RequestAttribute Long userId,
            @RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(ApiResponse.success(cozeAiService.getGeneratedContents(userId, contentType)));
    }
    
    @PostMapping("/contents/{id}/favorite")
    public ResponseEntity<ApiResponse<Void>> toggleFavorite(@PathVariable Long id) {
        cozeAiService.toggleFavorite(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    
    @DeleteMapping("/contents/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContent(@PathVariable Long id) {
        cozeAiService.deleteContent(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
