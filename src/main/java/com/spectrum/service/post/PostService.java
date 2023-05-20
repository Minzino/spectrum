package com.spectrum.service.post;

import com.spectrum.common.aop.UserValidation;
import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostPageResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.service.post.dto.PostCreateDto;
import com.spectrum.service.post.dto.PostDto;
import com.spectrum.service.post.dto.PostUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    @UserValidation
    public PostCreateResponse save(Long userId, PostCreateDto postCreateDto) {
        Post post = postCreateDto.convertToEntity(userId);
        return PostCreateResponse.ofEntity(postRepository.save(post));
    }

    @Transactional
    @UserValidation
    public PostUpdateResponse update(Long userId, Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        post.authorCheck(userId);

        post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), post.getUserId());
        return PostUpdateResponse.ofEntity(post);
    }

    @Transactional
    @UserValidation
    public void delete(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        post.authorCheck(userId);

        postRepository.delete(post);
    }

    public PostPageResponse findByPage(Long cursor, Pageable pageable) {
        Slice<Post> postSlice = postRepository.findPostsAfterId(cursor, pageable);

        List<PostDto> postDtos = postSlice.getContent().stream()
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .collect(Collectors.toList());

        Long lastCursor = null;
        if (!postDtos.isEmpty()) {
            PostDto lastPostDto = postDtos.get(postDtos.size() - 1);
            lastCursor = lastPostDto.getPostId();
        }

        return new PostPageResponse(
            postDtos,
            postSlice.getNumberOfElements(),
            lastCursor
        );
    }

    public PostDetailResponse findPostById(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);

        return PostDetailResponse.ofEntity(post);
    }

    public PostPageResponse searchPosts(Long cursor, String searchType, String searchValue, Pageable pageable) {
        Slice<Post> postSlice = postRepository.searchPosts(cursor, searchType, searchValue,
            pageable);

        List<PostDto> postDtos = postSlice.getContent().stream()
            .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
            .collect(Collectors.toList());

        Long lastCursor = null;
        if (!postDtos.isEmpty()) {
            PostDto lastPostDto = postDtos.get(postDtos.size() - 1);
            lastCursor = lastPostDto.getPostId();
        }

        return new PostPageResponse(
            postDtos,
            postSlice.getNumberOfElements(),
            lastCursor
        );
    }
}
