package com.prokectB.meongbti.board.service;


import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.board.repository.PostRepository;
import com.prokectB.meongbti.common.exception.notfound.PostNotFoundException;

import com.prokectB.meongbti.common.utils.paging.CursorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostRepository postRepository;

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public List<Post> getPosts(Long memberId, CursorRequest request) {
        return postRepository.findAllByLessThanIdAndMemberId(memberId, request);
    }

}
