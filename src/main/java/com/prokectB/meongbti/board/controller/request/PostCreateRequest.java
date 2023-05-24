package com.prokectB.meongbti.board.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank
    private String content;

    @NotNull
    private MultipartFile postImageFile;

    @Builder
    public PostCreateRequest(@NotBlank String content, @NotNull MultipartFile postImageFile) {
        this.content = content;
        this.postImageFile = postImageFile;
    }
}

