package com.prokectB.meongbti.board.dto.response;

import com.prokectB.meongbti.board.entity.Post;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class PostDetailResponse {
    private Long postId;
    private String content;
    private String postImageUrl;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDate createdDate;
    private Long memberId;
    private String nickname;
    private Boolean isPostLiked;

    @Builder
    public PostDetailResponse(Long postId, String content, String postImageUrl, Long likeCount, LocalDateTime createdAt, LocalDate createdDate, Long memberId, String nickname, Boolean isPostLiked) {
        this.postId = postId;
        this.content = content;
        this.postImageUrl = postImageUrl;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.nickname = nickname;
        this.isPostLiked = isPostLiked;
    }

    public static PostDetailResponse from(Post post, Boolean isPostLiked) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .postImageUrl(post.getPostImageUrl())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .createdDate(post.getCreatedDate())
                .memberId(post.getWriterUser().getId())
                .nickname(post.getWriterUser().getNickname())
                .isPostLiked(isPostLiked)
                .build();
    }
}