package com.prokectB.meongbti.authentication.oauth;

import com.prokectB.meongbti.member.entity.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}