package com.spectrum.service;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostListResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostDto;
import com.spectrum.service.dto.PostUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
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

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), memberId);
        return PostUpdateResponse.ofEntity(post);
    }

    @Transactional
    public void delete(Long memberId, Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }

    public PostListResponse findAll() {

        List<Post> postList = postRepository.findAll();
        List<PostDto> postsDto = postList.stream()
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .collect(Collectors.toList());
        return new PostListResponse(postsDto);
    }

    public PostDetailResponse findPostById(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.ofEntity(post);
    }
}
