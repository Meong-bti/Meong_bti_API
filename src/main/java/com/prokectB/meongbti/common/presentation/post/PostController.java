package com.prokectB.meongbti.common.presentation.post;

import com.prokectB.meongbti.aplication.usecase.*;

import com.prokectB.meongbti.board.controller.request.PostCreateRequest;
import com.prokectB.meongbti.board.controller.request.PostEditRequest;
import com.prokectB.meongbti.board.dto.response.PostDetailResponse;
import com.prokectB.meongbti.board.dto.response.PostLikeResponse;
import com.prokectB.meongbti.board.dto.response.PostResponse;
import com.prokectB.meongbti.common.presentation.auth.AuthenticatedMember;
import com.prokectB.meongbti.common.presentation.auth.Login;
import com.prokectB.meongbti.common.utils.paging.CursorRequest;
import com.prokectB.meongbti.common.utils.paging.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final CreatePostUsecase createPostUsecase;
    private final EditPostUsecase editPostUsecase;

    private final CreatePostLikeUsecase createPostLikeUsecase;

    private final DestroyPostLikeUsecase destroyPostLikeUsecase;

    private final GetPostUseCase getPostUseCase;

    @Login
    @PostMapping
    public PostResponse register(@AuthenticatedMember Long userId,
                                 @Validated PostCreateRequest postCreateRequest) throws IOException {
        return createPostUsecase.execute(userId, postCreateRequest);
    }

    @Login
    @PostMapping("/{postId}/edit")
    public PostResponse edit(@AuthenticatedMember Long userId,
                             @PathVariable Long postId,
                             @Validated PostEditRequest postEditRequest) throws IOException {
        return editPostUsecase.execute(userId, postId, postEditRequest);
    }


    @Login
    @PostMapping("/{postId}/like")
    public PostLikeResponse likePost(@AuthenticatedMember Long memberId,
                                     @PathVariable Long postId) {
        return createPostLikeUsecase.execute(memberId, postId);
    }

    @Login
    @PostMapping("/{postId}/dislike")
    public PostLikeResponse dislikePost(@AuthenticatedMember Long memberId,
                                        @PathVariable Long postId) {
        return destroyPostLikeUsecase.execute(memberId, postId);
    }

    @Login
    @GetMapping("/list")
    public PageCursor<PostDetailResponse> getFeed(@AuthenticatedMember Long userId,
                                                  CursorRequest cursorRequest) {
        return getPostUseCase.execute(userId, cursorRequest);
    }
}