package com.prokectB.meongbti.common.presentation.post;

import com.prokectB.meongbti.aplication.usecase.CreatePostUsecase;
import com.prokectB.meongbti.aplication.usecase.EditPostUsecase;

import com.prokectB.meongbti.board.controller.request.PostCreateRequest;
import com.prokectB.meongbti.board.controller.request.PostEditRequest;
import com.prokectB.meongbti.board.dto.response.PostResponse;
import com.prokectB.meongbti.common.presentation.auth.AuthenticatedMember;
import com.prokectB.meongbti.common.presentation.auth.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final CreatePostUsecase createPostUsecase;
    private final EditPostUsecase editPostUsecase;

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
}