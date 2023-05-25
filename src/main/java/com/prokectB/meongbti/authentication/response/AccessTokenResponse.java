package com.prokectB.meongbti.authentication.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AccessTokenResponse {
    private String accessToken;

    @Builder
    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static AccessTokenResponse from(String accessToken) {
        return new AccessTokenResponse(accessToken);
    }
}