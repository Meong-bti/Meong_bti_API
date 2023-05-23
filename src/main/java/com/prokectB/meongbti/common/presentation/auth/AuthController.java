package com.prokectB.meongbti.common.presentation.auth;

import com.prokectB.meongbti.authentication.service.AuthService;
import com.prokectB.meongbti.common.exception.unauthorized.RefreshTokenNotExistsException;
import com.prokectB.meongbti.common.presentation.auth.common.LoginDto;
import com.prokectB.meongbti.common.presentation.auth.request.LoginRequest;
import com.prokectB.meongbti.common.presentation.auth.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.prokectB.meongbti.common.presentation.auth.RefreshTokenCookieProvider.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        ResponseCookie cookie = refreshTokenCookieProvider.createCookie(loginDto.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(loginDto.getAccessToken()));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(value = REFRESH_TOKEN, required = false) String refreshToken) {
        validateRefreshTokenExists(refreshToken);
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookieProvider.createLogoutCookie().toString())
                .build();
    }

    private void validateRefreshTokenExists(final String refreshToken) {
        if (refreshToken == null) {
            throw new RefreshTokenNotExistsException();
        }
    }
}
