package com.prokectB.meongbti.auth;

import com.prokectB.meongbti.dto.common.LoginDto;
import com.prokectB.meongbti.application.auth.service.AuthService;
import com.prokectB.meongbti.application.auth.token.RefreshToken;
import com.prokectB.meongbti.application.auth.token.RefreshTokenProvider;
import com.prokectB.meongbti.application.auth.token.RefreshTokenRepository;
import com.prokectB.meongbti.application.auth.token.TokenProvider;
import com.prokectB.meongbti.exception.notfound.MemberNotFoundException;
import com.prokectB.meongbti.exception.unauthorized.PasswordMismatchException;
import com.prokectB.meongbti.exception.unauthorized.RefreshTokenExpiredException;
import com.prokectB.meongbti.exception.unauthorized.RefreshTokenNotExistsException;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.repository.MemberRepository;
import com.prokectB.meongbti.util.fixture.auth.RefreshTokenFixture;
import com.prokectB.meongbti.util.fixture.member.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private RefreshTokenProvider refreshTokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;
    @Test
    @DisplayName("로그인이 성공해야한다.")
    void loginWithCorrectCredentials() {

        String password = "password123";
        String email = "test@gmail.com";
        Long memberId = 1L;
        String refreshTokenValue = "refresh_token_value";
        String accessToken = "access_token";
        RefreshToken refreshToken = RefreshTokenFixture.create(refreshTokenValue, memberId, LocalDateTime.now().plusDays(7));
        Member member = MemberFixture.create(memberId, password, email);
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(password, member.getPassword())).thenReturn(true);
        when(tokenProvider.createAccessToken(memberId)).thenReturn(accessToken);
        when(refreshTokenProvider.createToken(memberId)).thenReturn(refreshToken);
        LoginDto result = authService.login(email, password);
        assertNotNull(result);
        assertEquals(accessToken, result.getAccessToken());
        assertEquals(refreshToken.getTokenValue(), result.getRefreshToken());
        verify(refreshTokenRepository, times(1)).save(refreshToken);
    }
    @Test
    @DisplayName("잘못된 비밀번호로 로그인을 하면 PasswordMismatchException이 발생한다.")
    void loginWithIncorrectPasswordThrowsPasswordMismatchException() {
        String email = "test@gmail.com";
        String password = "password123";
        long memberId = 1L;
        Member member = MemberFixture.create(memberId, email, password);
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(password, member.getPassword())).thenReturn(false);
        assertThrows(PasswordMismatchException.class, () -> authService.login(email, password));
    }
    @Test
    @DisplayName("존재하지 않은 User로 로그인을 시도하면 UserNotFoundException이 발생한다.")
    void loginWithNonexistentUserThrowsUserNotFoundException() {
        String email = "test_user";
        String password = "password123";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> authService.login(email, password));
    }

    @Test
    @DisplayName("토큰이 재발급 되어야한다.")
    void testReissueToken() {
        Long memberId = 1L;
        String refreshTokenValue = "valid-refresh-token-value";
        RefreshToken refreshToken = RefreshTokenFixture
                .create(refreshTokenValue, memberId, LocalDateTime.now().plusDays(1));

        when(refreshTokenRepository.findTokenByTokenValue(refreshTokenValue)).thenReturn(Optional.of(refreshToken));
        String newAccessToken = "new-access-token";
        RefreshToken newRefreshToken = RefreshTokenFixture
                .create("new-refresh-token-value", memberId, LocalDateTime.now().plusDays(7));
        when(tokenProvider.createAccessToken(memberId)).thenReturn(newAccessToken);
        when(refreshTokenProvider.createToken(memberId)).thenReturn(newRefreshToken);

        LoginDto loginDto = authService.reissueToken(refreshTokenValue);

        assertNotNull(loginDto);
        assertEquals(newAccessToken, loginDto.getAccessToken());
        assertEquals(newRefreshToken.getTokenValue(), loginDto.getRefreshToken());
        verify(refreshTokenRepository, times(1)).delete(refreshTokenValue);
        verify(refreshTokenRepository, times(1)).save(newRefreshToken);
    }

    @Test
    @DisplayName("DB에 없는 토큰이면 RefreshTokenNotExistsException이 발생한다.")
    public void reissueToken_invalidToken() {
        String refreshTokenValue = "invalid-refresh-token-value";
        when(refreshTokenRepository.findTokenByTokenValue(refreshTokenValue)).thenReturn(Optional.empty());

        assertThrows(RefreshTokenNotExistsException.class, () -> authService.reissueToken(refreshTokenValue));

        verify(refreshTokenRepository, times(1)).findTokenByTokenValue(refreshTokenValue);
        verify(refreshTokenRepository, never()).delete(anyString());
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    @DisplayName("만료된 토큰이면 발생한다.")
    public void reissueToken_expiredToken() {
        Long memberId = 1L;
        String refreshTokenValue = "expired-refresh-token-value";
        RefreshToken expiredRefreshToken = RefreshTokenFixture
                .create(refreshTokenValue, memberId, LocalDateTime.now().minusDays(1));
        when(refreshTokenRepository.findTokenByTokenValue(refreshTokenValue)).thenReturn(Optional.of(expiredRefreshToken));

        assertThrows(RefreshTokenExpiredException.class, () -> authService.reissueToken(refreshTokenValue));

        verify(refreshTokenRepository, times(1)).delete(refreshTokenValue);
    }
}