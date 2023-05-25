package com.prokectB.meongbti.authentication.controller;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    String email;

    String password;

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
