package com.prokectB.meongbti.authentication.service;

import com.prokectB.meongbti.authentication.token.RefreshToken;
import com.prokectB.meongbti.authentication.token.RefreshTokenProvider;
import com.prokectB.meongbti.authentication.token.RefreshTokenRepository;
import com.prokectB.meongbti.authentication.token.TokenProvider;
import com.prokectB.meongbti.common.exception.notfound.MemberNotFoundException;
import com.prokectB.meongbti.common.exception.unauthorized.PasswordMismatchException;
import com.prokectB.meongbti.common.presentation.auth.common.LoginDto;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginDto login(String email, String password) {
        Member loginMember = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        if (!passwordEncoder.matches(password, loginMember.getPassword())) {
            throw new PasswordMismatchException();
        }

        Long memberId = loginMember.getId();
        String accessToken = tokenProvider.createAccessToken(memberId);
        RefreshToken refreshToken = refreshTokenProvider.createToken(memberId);
        refreshTokenRepository.save(refreshToken);
        return LoginDto.create(accessToken, refreshToken.getTokenValue());
    }
}