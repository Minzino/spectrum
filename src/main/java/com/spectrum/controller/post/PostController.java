package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostListResponse;
import com.spectrum.controller.post.dto.PostUpdateRequest;
import com.spectrum.service.PostService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Long memberId,
        @Valid @RequestBody PostCreateRequest postCreateRequest) {

        PostCreateResponse createdPost = postService.save(memberId,
            postCreateRequest.convertToPostCreateDto());
        URI location = URI.create("/api/posts" + createdPost.getPostId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
        Long memberId,
        @PathVariable("postId") Long postId,
        @Valid @RequestBody PostUpdateRequest postUpdateRequest) {

        postService.update(memberId, postId, postUpdateRequest.convertToPostUpdateDto());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
        Long memberId,
        @PathVariable("postId") Long postId) {

        postService.delete(memberId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PostListResponse> getAllPosts() {

        PostListResponse postListResponse = postService.findAll();
        return ResponseEntity.ok().body(postListResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(
        @PathVariable("postId") Long postId) {

        PostDetailResponse postdetailResponse = postService.findPostById(postId);
        return ResponseEntity.ok().body(postdetailResponse);
    }
}
