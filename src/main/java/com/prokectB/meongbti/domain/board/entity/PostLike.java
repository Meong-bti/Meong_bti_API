package com.prokectB.meongbti.domain.board.entity;

import com.prokectB.meongbti.domain.Member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class PostLike {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(updatable = false)
    private LocalDateTime likedAt;

    @PrePersist
    private void onPrePersist() {
        this.likedAt = LocalDateTime.now();
    }

    @Builder
    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
