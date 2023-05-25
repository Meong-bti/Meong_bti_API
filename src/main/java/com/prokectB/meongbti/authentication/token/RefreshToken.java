package com.prokectB.meongbti.authentication.token;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RefreshToken {

    private String tokenValue;
    private Long memberId;
    private LocalDateTime expiredAt;
   @Builder
    public RefreshToken(String tokenValue, Long memberId, LocalDateTime expiredAt) {
        this.tokenValue = tokenValue;
        this.memberId = memberId;
        this.expiredAt = expiredAt;
    }

    public static RefreshToken create(Long memberId, long validTimeInDays) {
        return new RefreshToken(UUID.randomUUID().toString(), memberId, LocalDateTime.now().plusDays(validTimeInDays));
    }



    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }
}