package com.prokectB.meongbti.dto.request.board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PostEditRequest {
    @NotBlank
    private String content;

    @NotNull
    private MultipartFile postImageFile;

    @Builder
    public PostEditRequest(@NotBlank String content, @NotNull MultipartFile postImageFile) {
        this.content = content;
        this.postImageFile = postImageFile;
    }
}