package com.prokectB.meongbti.dto.response.board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeResponse {

    private Long postId;
    @Builder
    private PostLikeResponse(Long postId) {
        this.postId = postId;
    }

    public static PostLikeResponse from(Long postId) {
        return new PostLikeResponse(postId);
    }
}
