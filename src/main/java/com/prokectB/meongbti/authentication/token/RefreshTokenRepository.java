package com.prokectB.meongbti.authentication.token;


import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findTokenByTokenValue(String tokenValue);

    void delete(String tokenValue);
}