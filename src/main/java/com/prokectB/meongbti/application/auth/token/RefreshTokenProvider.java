package com.prokectB.meongbti.application.auth.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenProvider {

    private final long validTimeInDays;

    public RefreshTokenProvider(@Value("${token.refresh.valid-time-in-days}") long validTimeInDays) {
        this.validTimeInDays = validTimeInDays;
    }

    public RefreshToken createToken(Long memberId) {
        return RefreshToken.create(memberId, validTimeInDays);
    }
}