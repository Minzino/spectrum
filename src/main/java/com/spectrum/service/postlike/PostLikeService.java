package com.spectrum.service.postlike;

import com.spectrum.auth.aop.UserValidation;
import com.spectrum.controller.postlike.dto.PostLikeResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.postlike.PostLike;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.postlike.PostLikeRepository;
import com.spectrum.service.postlike.dto.PostLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    @UserValidation
    public PostLikeResponse save(Long userId, Long postId, PostLikeDto postLikeDto) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        PostLike postLike = postLikeDto.convertToEntity(userId, post.getId());
        return PostLikeResponse.ofEntity(postLikeRepository.save(postLike));
    }
}
