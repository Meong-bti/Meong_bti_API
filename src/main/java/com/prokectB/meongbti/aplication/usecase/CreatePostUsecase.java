package com.prokectB.meongbti.aplication.usecase;

import com.prokectB.meongbti.board.controller.request.PostCreateRequest;
import com.prokectB.meongbti.board.dto.response.PostResponse;
import com.prokectB.meongbti.board.entity.Post;
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
public class CreatePostUsecase {

    private final MemberReadService userReadService;
    private final PostWriteService postWriteService;
    private  final S3Uploader s3Uploader;




    public PostResponse execute(Long userId, PostCreateRequest postCreateRequest)  throws IOException {
        Member writerUser = userReadService.getMember(userId);
        String postImageUrl = s3Uploader.getImageUrl(postCreateRequest.getPostImageFile(), ImageStorageFolderName.POST_IMAGE_PATH);

        Post newPost = postWriteService.register(writerUser, postCreateRequest.getContent(), postImageUrl);

        return PostResponse.from(newPost);
    }
}
