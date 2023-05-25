package com.prokectB.meongbti.authentication.oauth;

import com.prokectB.meongbti.member.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
