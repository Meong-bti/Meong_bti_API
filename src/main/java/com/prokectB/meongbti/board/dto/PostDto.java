package com.prokectB.meongbti.board.dto;

import com.prokectB.meongbti.board.entity.Post;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostDto {

    private Post post;
    private String willDeleteImageUrl;

    @Builder
    public PostDto(Post post, String willDeleteImageUrl) {
        this.post = post;
        this.willDeleteImageUrl = willDeleteImageUrl;
    }

    public static PostDto from(Post post, String willDeleteImageUrl) {
        return new PostDto(post, willDeleteImageUrl);
    }

    public static PostDto from(Post post) {
        return new PostDto(post, null);
    }
}