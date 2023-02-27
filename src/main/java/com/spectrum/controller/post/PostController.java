package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostDto;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        PostDto postDto = new PostDto(postCreateRequest.getTitle(), postCreateRequest.getContent());

        return ResponseEntity.created(URI.create("/api/posts")).build();
    }
}
