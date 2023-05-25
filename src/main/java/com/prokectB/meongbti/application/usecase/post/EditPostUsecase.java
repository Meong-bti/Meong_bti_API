package com.prokectB.meongbti.application.usecase.post;


import com.prokectB.meongbti.dto.request.board.PostEditRequest;
import com.prokectB.meongbti.dto.common.PostDto;
import com.prokectB.meongbti.dto.response.board.PostResponse;
import com.prokectB.meongbti.domain.board.service.PostWriteService;
import com.prokectB.meongbti.support.file.ImageStorageFolderName;
import com.prokectB.meongbti.config.image.S3Uploader;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EditPostUsecase {

    private final MemberReadService userReadService;
    private final PostWriteService postWriteService;
    private  final S3Uploader s3Uploader;

    public PostResponse execute(Long userId, Long postId, PostEditRequest postEditRequest) throws IOException {
        Member member = userReadService.getMember(userId);

        if (postEditRequest.getPostImageFile() == null || postEditRequest.getPostImageFile().isEmpty()) {
            PostDto editedPost = postWriteService.edit(member, postId, postEditRequest.getContent(), null);
            return PostResponse.from(editedPost.getPost());
        }

        String uploadedImage = s3Uploader.getImageUrl(postEditRequest.getPostImageFile(), ImageStorageFolderName.POST_IMAGE_PATH);
        PostDto editedPost = postWriteService.edit(member, postId, postEditRequest.getContent(), uploadedImage);
        s3Uploader.deleteImageFile(editedPost.getWillDeleteImageUrl());
        return PostResponse.from(editedPost.getPost());
    }
}