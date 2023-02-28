package com.spectrum.service;

import com.spectrum.domain.post.Post;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void save(PostCreateDto postCreateDto) {

        Post post = postCreateDto.convertToEntity();
        postRepository.save(post);
    }
}
