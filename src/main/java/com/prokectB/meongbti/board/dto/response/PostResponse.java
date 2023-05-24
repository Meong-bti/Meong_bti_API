package com.prokectB.meongbti.board.dto.response;

import com.prokectB.meongbti.board.entity.Post;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String postImageUrl;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;

    @Builder
    public PostResponse(Long id, String postImageUrl, String content, Long likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.postImageUrl = postImageUrl;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;

    }


    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(), post.getPostImageUrl(), post.getContent(), post.getLikeCount(), post.getCreatedAt());
    }
}
