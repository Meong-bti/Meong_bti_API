package com.prokectB.meongbti.board.service;


import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.board.repository.PostLikeRepository;
import com.prokectB.meongbti.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeReadService {

    private final PostLikeRepository postLikeRepository;

    public List<Boolean> getPostLikes(Member member, List<Post> posts) {
        List<Boolean> isPostLikes = postLikeRepository.findAllByUserAndPosts(member, posts);
        Collections.reverse(isPostLikes);
        return isPostLikes;
    }
}
