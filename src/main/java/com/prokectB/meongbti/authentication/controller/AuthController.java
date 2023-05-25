package com.prokectB.meongbti.authentication.controller;

import com.prokectB.meongbti.dto.common.LoginDto;

import com.prokectB.meongbti.authentication.response.AccessTokenResponse;
import com.prokectB.meongbti.application.auth.service.AuthService;
import com.prokectB.meongbti.exception.unauthorized.RefreshTokenNotExistsException;

import com.prokectB.meongbti.presentation.auth.RefreshTokenCookieProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.prokectB.meongbti.presentation.auth.RefreshTokenCookieProvider.REFRESH_TOKEN;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(loginDto.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AccessTokenResponse.from(loginDto.getAccessToken()));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken) {
        validateRefreshTokenExists(refreshToken);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.createLogoutCookie().toString())
                .build();
    }


    @PostMapping("/reissueToken")
    public ResponseEntity<AccessTokenResponse> reissueToken(
            @CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken) {
        validateRefreshTokenExists(refreshToken);
        LoginDto loginDto = authService.reissueToken(refreshToken);
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(loginDto.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AccessTokenResponse.from(loginDto.getAccessToken()));
    }


    private void validateRefreshTokenExists(final String refreshToken) {
        if (refreshToken == null) {
            throw new RefreshTokenNotExistsException();
        }
    }
}
