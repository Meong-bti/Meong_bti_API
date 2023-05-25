package com.prokectB.meongbti.domain.Member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String nickname;

    private String password;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    private void onPrePersist() {
        this.joinedAt = LocalDateTime.now();
    }

    @Builder
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;

    }

    public static Member create(String email, String encodedPassword, String nickname) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(encodedPassword)

                .build();
    }
}
