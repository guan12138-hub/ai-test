package com.aiingredient.dto;

import lombok.Data;

@Data
public class CozeRequest {
    private String query;
    private String userId;
    private String conversationId;
    private String contentType;
}

@Data
class CozeMessage {
    private String role;
    private String content;
    private String type;
}
