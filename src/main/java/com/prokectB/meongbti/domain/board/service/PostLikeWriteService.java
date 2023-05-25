package com.prokectB.meongbti.domain.board.service;

import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.domain.board.entity.PostLike;
import com.prokectB.meongbti.domain.board.repository.PostLikeRepository;
import com.prokectB.meongbti.exception.badrequest.AlreadyLikedPostException;
import com.prokectB.meongbti.exception.badrequest.NotLikedPostException;
import com.prokectB.meongbti.domain.Member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void deletePostLike(Post post) {
        List<PostLike> postLikes = postLikeRepository.findAllByPost(post);
        postLikeRepository.deleteAll(postLikes);
    }
}