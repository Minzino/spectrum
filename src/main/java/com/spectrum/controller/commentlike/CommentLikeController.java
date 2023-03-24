package com.spectrum.controller.commentlike;

import com.spectrum.common.resolver.CurrentUser;
import com.spectrum.controller.commentlike.dto.CommentLikeRequest;
import com.spectrum.controller.commentlike.dto.CommentLikeResponse;
import com.spectrum.service.commentlike.CommentLikeService;
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
@RequestMapping("/api/comments/{commentId}/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<CommentLikeResponse> createCommentLike(
        @CurrentUser Long userId,
        @PathVariable("commentId") Long commentId,
        @Valid @RequestBody CommentLikeRequest commentLikeRequest) {

        CommentLikeResponse commentLikeResponse = commentLikeService.save(
            userId
            , commentId
            , commentLikeRequest.convertToCommentLikeDto()
        );

        URI location = URI.create("/api/comments/" + commentLikeResponse.getCommentId() + "/likes");
        return ResponseEntity.created(location).build();
    }
}
