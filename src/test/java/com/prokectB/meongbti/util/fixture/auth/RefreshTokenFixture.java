package com.prokectB.meongbti.util.fixture.auth;

import com.prokectB.meongbti.authentication.token.RefreshToken;

import java.time.LocalDateTime;

public class RefreshTokenFixture {

    public static RefreshToken create(String tokenValue, Long memberId ,LocalDateTime expiredAt) {
        return new RefreshToken(tokenValue, memberId, expiredAt);
    }
}