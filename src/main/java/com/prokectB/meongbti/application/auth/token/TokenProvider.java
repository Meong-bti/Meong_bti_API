package com.prokectB.meongbti.application.auth.token;

public interface TokenProvider {

    String createAccessToken(Long id);

    boolean isValidToken(String authorizationHeader);

    Long getmemberId(String authorizationHeader);
}