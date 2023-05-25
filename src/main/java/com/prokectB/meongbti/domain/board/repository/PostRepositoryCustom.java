package com.prokectB.meongbti.domain.board.repository;

import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.support.paging.CursorRequest;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllByLessThanIdAndMemberId(Long memberId, CursorRequest cursorRequest);
}
