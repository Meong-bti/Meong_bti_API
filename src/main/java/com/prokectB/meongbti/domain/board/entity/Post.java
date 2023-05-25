package com.prokectB.meongbti.domain.board.entity;

import com.prokectB.meongbti.domain.Member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String postImageUrl;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writerUser;

    private Long likeCount;

    @Column(updatable = false)
    private LocalDate createdDate;

    @Column(updatable = false)
    private LocalDateTime createdAt;




    @Version
    private Long version;

    @PrePersist
    private void onPrePersist() {
        this.likeCount = 0L;
        this.createdDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Post(String postImageUrl, String content, Member writerUser ) {
        this.postImageUrl = postImageUrl;
        this.content = content;
        this.writerUser = writerUser;
    }

    public boolean isWriter(Member member) {
        return writerUser == member;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public String updatePostImageUrl(String postImageUrl) {
        String willDeleteImageUrl = this.postImageUrl;
        this.postImageUrl = postImageUrl;
        return willDeleteImageUrl;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }
}