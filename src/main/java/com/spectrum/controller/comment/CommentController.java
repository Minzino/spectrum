package com.spectrum.controller.comment;

import com.spectrum.common.resolver.CurrentUser;
import com.spectrum.controller.comment.dto.CommentCreateRequest;
import com.spectrum.controller.comment.dto.CommentCreateResponse;
import com.spectrum.controller.comment.dto.CommentUpdateRequest;
import com.spectrum.service.comment.CommentService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(
        @CurrentUser Long userId,
        @PathVariable("postId") Long postId,
        @Valid @RequestBody CommentCreateRequest commentCreateRequest) {

        CommentCreateResponse createdComment = commentService.save(
            userId
            , postId
            , commentCreateRequest.convertToCommentCreateDto()
        );
        URI location = URI.create("/api/posts/" + createdComment.getPostId() + "/comments");
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
        @CurrentUser Long userId,
        @PathVariable("postId") Long postId,
        @PathVariable("commentId") Long commentId,
        @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {

        commentService.update(
            userId
            , postId
            , commentId
            , commentUpdateRequest.convertToCommentUpdateDto()
        );
        return ResponseEntity.ok().build();
    }

}
