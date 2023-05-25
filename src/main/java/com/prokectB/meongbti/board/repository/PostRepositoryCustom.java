package com.prokectB.meongbti.board.repository;

import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.common.utils.paging.CursorRequest;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllByLessThanIdAndMemberId(Long memberId, CursorRequest cursorRequest);
}
