package com.prokectB.meongbti.application.usecase.post;

import com.prokectB.meongbti.dto.response.board.PostDetailResponse;
import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.domain.board.service.PostLikeReadService;
import com.prokectB.meongbti.domain.board.service.PostReadService;
import com.prokectB.meongbti.support.paging.CursorRequest;
import com.prokectB.meongbti.support.paging.PageCursor;
import com.prokectB.meongbti.domain.Member.entity.Member;
import com.prokectB.meongbti.domain.Member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPostUseCase {

    private final MemberReadService memberReadService;
    private final PostReadService postReadService;
    private final PostLikeReadService postLikeReadService;

    public PageCursor<PostDetailResponse> execute(Long memberId, CursorRequest cursorRequest) {
        Member member = memberReadService.getMember(memberId);
        List<Post> posts = postReadService.getPosts(member.getId(), cursorRequest);

        Long nextKey = posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);


        Iterator<Boolean> isPostLikedIterator = postLikeReadService.getPostLikes(member, posts).iterator();

        List<PostDetailResponse> postDetailResponses = posts.stream()
                .map(post -> PostDetailResponse.from(post, isPostLikedIterator.next()))
                .collect(Collectors.toList());

        return PageCursor.from(cursorRequest.next(nextKey), postDetailResponses);
    }
}