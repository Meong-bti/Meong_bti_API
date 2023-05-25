package com.prokectB.meongbti.domain.board.service;

import com.prokectB.meongbti.dto.common.PostDto;
import com.prokectB.meongbti.domain.board.entity.Post;
import com.prokectB.meongbti.domain.board.repository.PostRepository;
import com.prokectB.meongbti.exception.badrequest.NotPostWriterException;
import com.prokectB.meongbti.exception.notfound.PostNotFoundException;
import com.prokectB.meongbti.domain.Member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostWriteService {

    private final PostRepository postRepository;

    public Post register(Member writerUser, String content, String postImageUrl) {
        Post newPost = Post.builder()
                .writerUser(writerUser)
                .content(content)
                .postImageUrl(postImageUrl)
                .build();
        return postRepository.save(newPost);
    }

    public PostDto edit(Member member, Long postId, String content, String postImageUrl) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        checkWriter(member, post);
        post.updateContent(content);
        if (postImageUrl != null) {
            String willDeleteImageUrl = post.updatePostImageUrl(postImageUrl);
            return PostDto.from(post, willDeleteImageUrl);
        }
        return PostDto.from(post);
    }
    public void upLikeCount(Post post) {
        post.incrementLikeCount();
    }

    public void downLikeCount(Post post) {
        post.decrementLikeCount();
    }
    private static void checkWriter(Member member, Post post) {
        if (!post.isWriter(member)) {
            throw new NotPostWriterException();
        }
    }

    public void deletePost(Member member, Post post) {
        checkWriter(member, post);
        postRepository.delete(post);
    }

}