package com.prokectB.meongbti.dto.response.member;

import com.prokectB.meongbti.domain.Member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime joinedAt;

    public UserResponse(Long id, String nickname, String email, LocalDateTime joinedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.joinedAt = joinedAt;
    }

    public static UserResponse create(Member member) {
        return new UserResponse(member.getId(), member.getNickname(),
                member.getEmail(), member.getJoinedAt());
    }
}