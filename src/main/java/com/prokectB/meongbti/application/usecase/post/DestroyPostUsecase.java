package com.prokectB.meongbti.application.usecase.post;


import com.prokectB.meongbti.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestroyPostUsecase {

    private final MemberReadService userReadService;
    private final PostReadService postReadService;
    private final PostLikeWriteService postLikeWriteService;
            private final PostWriteService postWriteService;

            public void execute(Long userId, Long postId) {
                Member member = userReadService.getMember(userId);
                Post post = postReadService.getPost(postId);
                if (post.isWriter(member)) {
                    postLikeWriteService.deletePostLike(post);

        }
        postWriteService.deletePost(member, post);
    }
}