package com.spectrum.controller.post;

import com.spectrum.common.resolver.CurrentUser;
import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostPageResponse;
import com.spectrum.controller.post.dto.PostUpdateRequest;
import com.spectrum.exception.post.InvalidPaginationException;
import com.spectrum.exception.post.InvalidSearchTypeException;
import com.spectrum.service.post.PostService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(
        @CurrentUser Long userId,
        @Valid @RequestBody PostCreateRequest postCreateRequest) {

        PostCreateResponse createdPost = postService.save(userId,
            postCreateRequest.convertToPostCreateDto());
        URI location = URI.create("/api/posts/" + createdPost.getPostId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
        @CurrentUser Long userId,
        @PathVariable("postId") Long postId,
        @Valid @RequestBody PostUpdateRequest postUpdateRequest) {

        postService.update(userId, postId, postUpdateRequest.convertToPostUpdateDto());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
        @CurrentUser Long userId,
        @PathVariable("postId") Long postId) {

        postService.delete(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> getPostsByPage(
        @RequestParam(value = "last-post-id", required = false) Long lastPostId,
        @RequestParam(defaultValue = "10") int size) {

        if (size != 10) {
            throw new InvalidPaginationException();
        }

        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        PostPageResponse postPageResponse = postService.findByPage(lastPostId, pageable);
        return ResponseEntity.ok().body(postPageResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(
        @PathVariable("postId") Long postId) {

        PostDetailResponse postdetailResponse = postService.findPostById(postId);
        return ResponseEntity.ok(postdetailResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<PostPageResponse> searchPosts(
        @RequestParam("type") String searchType
        , @RequestParam("value") String searchValue
        , @RequestParam(value = "last-post-id", required = false) Long lastPostId
        , @RequestParam(defaultValue = "10") int size) {

        try {
            SearchType.valueOf(searchType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSearchTypeException();
        }

        if (size != 10) {
            throw new InvalidPaginationException();
        }

        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        PostPageResponse response = postService.searchPosts(
            searchType
            , searchValue
            , lastPostId
            , pageable
        );
        return ResponseEntity.ok(response);
    }
}
