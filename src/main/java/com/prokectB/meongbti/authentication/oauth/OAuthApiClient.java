package com.prokectB.meongbti.authentication.oauth;

import com.prokectB.meongbti.member.entity.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
