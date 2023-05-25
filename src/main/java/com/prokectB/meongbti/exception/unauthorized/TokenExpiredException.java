package com.prokectB.meongbti.exception.unauthorized;

public class TokenExpiredException extends UnauthorizedException {

    public TokenExpiredException() {
        addValidation("token", "토큰이 만료되었습니다.");
    }
}