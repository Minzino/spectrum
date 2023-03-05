package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostUpdateRequest;
import com.spectrum.service.PostService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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
}
