package com.spectrum.service;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostListResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.User;
import com.spectrum.exception.post.NotAuthorException;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostDto;
import com.spectrum.service.dto.PostUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateResponse save(Long userId, PostCreateDto postCreateDto) {

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        Post post = postCreateDto.convertToEntity(user.getId());
        return PostCreateResponse.ofEntity(postRepository.save(post));
    }

    @Transactional
    public PostUpdateResponse update(Long userId, Long postId, PostUpdateDto postUpdateDto) {

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        if (!post.getUserId().equals(user.getId())) {
            throw new NotAuthorException();
        }

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), user.getId());
        return PostUpdateResponse.ofEntity(post);
    }

    @Transactional
    public void delete(Long userId, Long postId) {

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        if (!post.getUserId().equals(user.getId())) {
            throw new NotAuthorException();
        }

        postRepository.delete(post);
    }

    public PostListResponse findAll() {

        List<Post> postList = postRepository.findAll();
        List<PostDto> postsDto = postList.stream()
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .collect(Collectors.toUnmodifiableList());
        return new PostListResponse(postsDto);
    }

    public PostDetailResponse findPostById(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.ofEntity(post);
    }
}
