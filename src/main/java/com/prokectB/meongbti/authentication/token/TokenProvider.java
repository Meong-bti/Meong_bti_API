package com.prokectB.meongbti.authentication.token;

public interface TokenProvider {

    String createAccessToken(Long id);

    boolean isValidToken(String authorizationHeader);

    Long getmemberId(String authorizationHeader);
}