package com.prokectB.meongbti.application.usecase.post;

import com.prokectB.meongbti.dto.request.board.PostCreateRequest;
import com.prokectB.meongbti.dto.response.board.PostResponse;
import com.prokectB.meongbti.domain.board.entity.Post;
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
