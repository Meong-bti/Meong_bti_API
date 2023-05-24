package com.prokectB.meongbti.authentication.oauth.service;

import com.prokectB.meongbti.authentication.oauth.token.AuthTokensGenerator;
import com.prokectB.meongbti.authentication.oauth.OAuthInfoResponse;
import com.prokectB.meongbti.authentication.oauth.OAuthLoginParams;
import com.prokectB.meongbti.authentication.oauth.RequestOAuthInfoService;
import com.prokectB.meongbti.authentication.oauth.token.AuthTokens;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }
}