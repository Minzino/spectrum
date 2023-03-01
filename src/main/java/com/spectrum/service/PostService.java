package com.spectrum.service;

import com.spectrum.domain.post.Post;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse save(PostCreateDto postCreateDto) {

        Post post = postCreateDto.convertToEntity();
        return new PostCreateResponse(postRepository.save(post));
    }
}
