package com.prokectB.meongbti.common.presentation.auth.response;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginResponse {

    String accessToken;

    @Builder
    public LoginResponse(String accessToken) {
            this.accessToken = accessToken;
    }

    public static LoginResponse from(String accessToken) {
        return new LoginResponse(accessToken);
    }

}
