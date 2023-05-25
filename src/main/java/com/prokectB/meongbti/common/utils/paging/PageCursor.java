package com.prokectB.meongbti.common.utils.paging;

import com.prokectB.meongbti.board.dto.response.PostDetailResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageCursor<T> {

    private CursorRequest nextCursorRequest;
    private List<T> posts;

    public PageCursor(CursorRequest nextCursorRequest, List<T> posts) {
        this.nextCursorRequest = nextCursorRequest;
        this.posts = posts;
    }

    public static PageCursor<PostDetailResponse> from(CursorRequest nextCursorRequest, List<PostDetailResponse> posts) {
        return new PageCursor<>(nextCursorRequest, posts);
    }
}

