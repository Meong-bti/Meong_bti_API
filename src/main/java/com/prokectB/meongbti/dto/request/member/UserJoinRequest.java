package com.prokectB.meongbti.dto.request.member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserJoinRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
@Builder
    public UserJoinRequest(@NotBlank String email, @NotBlank String password,  @NotBlank String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
