package com.prokectB.meongbti.application.usecase.post;

import com.prokectB.meongbti.dto.response.board.PostLikeResponse;
import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.domain.board.service.PostLikeWriteService;
import com.prokectB.meongbti.domain.board.service.PostReadService;
import com.prokectB.meongbti.domain.board.service.PostWriteService;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostLikeUsecase {

    private final MemberReadService userReadService;
    private final PostReadService postReadService;
    private final PostLikeWriteService postLikeWriteService;
    private final PostWriteService postWriteService;

    public PostLikeResponse execute(Long userId, Long postId) {
        Member member = userReadService.getMember(userId);
        Post post = postReadService.getPost(postId);
        postLikeWriteService.like(member, post);

        // TODO 어떻게 바꿔야할지 고민해 볼 것
        try {
            postWriteService.upLikeCount(post);
        } catch (ObjectOptimisticLockingFailureException | StaleStateException e) {

        }
        return PostLikeResponse.from(postId);
    }
}