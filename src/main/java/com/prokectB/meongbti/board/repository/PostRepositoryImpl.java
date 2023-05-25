package com.prokectB.meongbti.board.repository;

import com.prokectB.meongbti.board.entity.Post;
import com.prokectB.meongbti.board.entity.QPost;
import com.prokectB.meongbti.common.utils.paging.CursorRequest;
import com.prokectB.meongbti.member.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;



import java.util.List;
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    QPost post = QPost.post;
    QMember member = QMember.member;




    @Override
    public List<Post> findAllByLessThanIdAndMemberId(Long memberId, CursorRequest cursorRequest) {
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.writerUser, member).fetchJoin()
                .where(
                        post.writerUser.id.eq(memberId),
                        lessThanPostId(cursorRequest)
                )
                .orderBy(post.createdAt.desc())
                .limit(cursorRequest.getSize())
                .fetch();
    }

    private BooleanExpression lessThanPostId(CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return post.id.lt(cursorRequest.getKey());
        }
        return null; // Return null instead of post.id.isNull()
    }
}


