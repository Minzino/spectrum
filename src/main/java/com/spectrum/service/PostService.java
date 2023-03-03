package com.spectrum.service;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostUpdateDto;
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
    public PostCreateResponse save(Long memberId, PostCreateDto postCreateDto) {

        Post post = postCreateDto.convertToEntity(memberId);
        return PostCreateResponse.ofEntity(postRepository.save(post));
    }

    @Transactional
    public PostUpdateResponse update(Long memberId, Long postId, PostUpdateDto postUpdateDto) {

        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        post.update(postUpdateDto, memberId);
        return PostUpdateResponse.ofEntity(post);
    }
}
