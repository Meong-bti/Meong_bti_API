package com.prokectB.meongbti.common.presentation.auth.common;


import lombok.Builder;
import lombok.Data;


@Data
public class LoginDto {

    String accessToken;
    String refreshToken;

    @Builder
    public LoginDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginDto create(String accessToken, String refreshToken) {
        return new LoginDto(accessToken, refreshToken);
    }
}
