package com.prokectB.meongbti.common.dto.response;


import lombok.Builder;
import lombok.Data;


import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private Map<String, String> validation;

    @Builder
    public ErrorResponse(int code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new ConcurrentHashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}