package com.prokectB.meongbti.presentation.board;

import com.prokectB.meongbti.application.usecase.post.*;
import com.prokectB.meongbti.dto.request.board.PostCreateRequest;
import com.prokectB.meongbti.dto.request.board.PostEditRequest;
import com.prokectB.meongbti.dto.response.board.PostDetailResponse;
import com.prokectB.meongbti.dto.response.board.PostLikeResponse;
import com.prokectB.meongbti.dto.response.board.PostResponse;
import com.prokectB.meongbti.presentation.auth.AuthenticatedMember;
import com.prokectB.meongbti.presentation.auth.Login;
import com.prokectB.meongbti.support.paging.CursorRequest;
import com.prokectB.meongbti.support.paging.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private final DestroyPostUsecase destroyPostUsecase;

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

    @Login
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticatedMember Long userId,
                                           @PathVariable Long postId) {
        destroyPostUsecase.execute(userId, postId);
        return ResponseEntity.noContent().build();
    }
}