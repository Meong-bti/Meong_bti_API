package com.prokectB.meongbti.aplication.usecase;

import com.prokectB.meongbti.board.dto.response.PostLikeResponse;
import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.board.service.PostLikeWriteService;
import com.prokectB.meongbti.board.service.PostReadService;
import com.prokectB.meongbti.board.service.PostWriteService;
import com.prokectB.meongbti.member.entity.Member;
import com.prokectB.meongbti.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestroyPostLikeUsecase {

    private final MemberReadService userReadService;
    private final PostReadService postReadService;
    private final PostLikeWriteService postLikeWriteService;
    private final PostWriteService postWriteService;

    public PostLikeResponse execute(Long memberId, Long postId) {
        Member member = userReadService.getMember(memberId);
        Post post = postReadService.getPost(postId);
        postLikeWriteService.dislike(member, post);

        // TODO 어떻게 바꿔야할지 고민해 볼 것
        try {
            postWriteService.downLikeCount(post);
        } catch (ObjectOptimisticLockingFailureException | StaleStateException ignored) {

        }
        return PostLikeResponse.from(postId);
    }
}