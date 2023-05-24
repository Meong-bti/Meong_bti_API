package com.prokectB.meongbti.aplication.usecase;


import com.prokectB.meongbti.board.controller.request.PostEditRequest;
import com.prokectB.meongbti.board.dto.PostDto;
import com.prokectB.meongbti.board.dto.response.PostResponse;
import com.prokectB.meongbti.board.service.PostWriteService;
import com.prokectB.meongbti.common.utils.ImageStorageFolderName;
import com.prokectB.meongbti.common.utils.image.S3Uploader;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.service.MemberReadService;
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