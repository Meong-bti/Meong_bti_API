package com.prokectB.meongbti.exception.unauthorized;

public class RefreshTokenExpiredException extends UnauthorizedException {

    public RefreshTokenExpiredException() {
        addValidation("refreshToken", "토큰이 만료되었습니다.");
    }
}