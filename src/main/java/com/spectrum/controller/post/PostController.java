package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.service.PostService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest postCreateRequest) {

        postService.save(postCreateRequest.convertToPostCreateDto());
        return ResponseEntity.created(URI.create("/api/posts")).build();
    }
}
