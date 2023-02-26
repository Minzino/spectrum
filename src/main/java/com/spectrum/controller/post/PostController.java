package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostRegisterRequest;
import com.spectrum.controller.post.dto.PostRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class PostController {

    @PostMapping("/api/posts")
    public ResponseEntity<PostRegisterResponse> create(@RequestBody PostRegisterRequest postRequest) {
        PostRegisterResponse postResponse = new PostRegisterResponse("1", postRequest.getContent(), postRequest.getTitle());
        return ResponseEntity.created(URI.create("/api/posts/")).body(postResponse);
    }
}
