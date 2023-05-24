package com.prokectB.meongbti.auth;


import com.prokectB.meongbti.authentication.token.AuthTokenExtractor;
import com.prokectB.meongbti.authentication.token.JwtTokenProvider;
import com.prokectB.meongbti.common.exception.unauthorized.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private static final String secretKey = "TestLJNO9KUPS2RN+HVVUYT9LUDMUAGIMUEMKVXR4DH5I=";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            new AuthTokenExtractor(), secretKey, 7200000L
    );

    @Test
    @DisplayName("토큰이 생성된다.")
    void createToken() {
        Long memberId = 123L;
        String token = jwtTokenProvider.createAccessToken(memberId);

        assertNotNull(token);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);

        assertEquals(ACCESS_TOKEN_SUBJECT, claims.getBody().getSubject());
        assertEquals(memberId, claims.getBody().get("id", Long.class));
    }

    @Test
    @DisplayName("유효한 토큰이면 True를 반환한다.")
    void isValidTokenShouldReturnTrueForValidToken() {
        Long memberId = 123L;
        String token = jwtTokenProvider.createAccessToken(memberId);

        boolean isValid = jwtTokenProvider.isValidToken("Bearer " + token);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("memberId로 토큰을 만들면 payload에서 같은 memberId를 반환해야한다.")
    void getmemberIdShouldReturnValidmemberIdForValidToken() {
        Long memberId = 123L;
        String token = jwtTokenProvider.createAccessToken(memberId);

        Long resultmemberId = jwtTokenProvider.getmemberId("Bearer " + token);

        assertEquals(memberId, resultmemberId);
    }

    @Test
    @DisplayName("유효기간이 지난 토큰은 TokenExpiredException이 발생한다.")
    void getmemberIdShouldThrowTokenExpiredExceptionForExpiredToken() {
        Long memberId = 123L;
        jwtTokenProvider = new JwtTokenProvider(new AuthTokenExtractor(), secretKey, -1000L);
        String token = jwtTokenProvider.createAccessToken(memberId);

        assertThrows(TokenExpiredException.class, () -> jwtTokenProvider.getmemberId("Bearer " + token));
    }

    @Test
    @DisplayName("유효하지 않은 토큰은 false를 반환한다.")
    void isValidTokenShouldReturnFalseForInvalidToken() {
        String token = "invalid-token";

        boolean isValid = jwtTokenProvider.isValidToken("Bearer " + token);

        assertFalse(isValid);
    }
}