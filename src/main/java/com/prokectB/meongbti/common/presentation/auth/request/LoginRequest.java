package com.prokectB.meongbti.common.presentation.auth.request;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequest {

    String email;

    String password;

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
