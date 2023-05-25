package com.prokectB.meongbti.board.service;

import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.board.entity.PostLike;
import com.prokectB.meongbti.board.repository.PostLikeRepository;
import com.prokectB.meongbti.common.exception.badrequest.AlreadyLikedPostException;
import com.prokectB.meongbti.common.exception.badrequest.NotLikedPostException;
import com.prokectB.meongbti.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public void like(Member member, Post post) {
        checkLiked(member, post);
        PostLike newPostLike = PostLike.builder().member(member).post(post).build();
        postLikeRepository.save(newPostLike);
    }

    public void dislike(Member member, Post post) {
        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post)
                .orElseThrow(NotLikedPostException::new);
        postLikeRepository.delete(postLike);
    }

    private void checkLiked(Member member, Post post) {
        if (postLikeRepository.existsByMemberAndPost(member, post)) {
            throw new AlreadyLikedPostException();
        }
    }
}