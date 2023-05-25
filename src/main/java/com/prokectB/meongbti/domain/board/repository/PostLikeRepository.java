package com.prokectB.meongbti.domain.board.repository;

import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.domain.board.entity.PostLike;
import com.prokectB.meongbti.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    @Query("select" +
            "    case" +
            "        when (select count(pl.id) from PostLike pl where pl.post = p and pl.member = :member) > 0" +
            "        then true" +
            "        else false" +
            "    end" +
            " from Post p" +
            " where p in :posts"
    )
    List<Boolean> findAllByUserAndPosts(@Param("member") Member member, @Param("posts") List<Post> posts);

    List<PostLike> findAllByPost(Post post);
}