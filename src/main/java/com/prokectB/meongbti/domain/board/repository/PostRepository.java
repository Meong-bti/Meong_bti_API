package com.prokectB.meongbti.domain.board.repository;

import com.prokectB.meongbti.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}