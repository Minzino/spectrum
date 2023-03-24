package com.spectrum.controller.postlike;

import com.spectrum.common.resolver.CurrentUser;
import com.spectrum.controller.postlike.dto.PostLikeResponse;
import com.spectrum.controller.postlike.dto.PostLikeRequest;
import com.spectrum.service.postlike.PostLikeService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<PostLikeResponse> createPostLike(
        @CurrentUser Long userId,
        @PathVariable("postId") Long postId,
        @Valid @RequestBody PostLikeRequest postLikeRequest) {

        PostLikeResponse postLikeResponse = postLikeService.save(
            userId
            , postId
            , postLikeRequest.convertToPostLikeDto()
        );

        URI location = URI.create("/api/posts/" + postLikeResponse.getPostId() + "/likes");
        return ResponseEntity.created(location).build();
    }
}
