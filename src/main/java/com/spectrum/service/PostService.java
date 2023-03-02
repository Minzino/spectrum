package com.spectrum.service;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostUpdateDto;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostCreateResponse save(PostCreateDto postCreateDto) {

        Post post = postCreateDto.convertToEntity();
        return PostCreateResponse.ofEntity(postRepository.save(post));
    }

    @Transactional
    public PostUpdateResponse update(Long memberId, Long postId, PostUpdateDto postUpdateDto) {

        Post findPost = postRepository.findById(postId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));

        Post updatePost = postRepository.save(findPost.update(postUpdateDto, memberId));
        return PostUpdateResponse.ofEntity(updatePost);
    }
}
