package com.spectrum.controller.comment.dto;

import com.spectrum.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateResponse {

    private Long userId;
    private Long postId;
    private String content;

    public CommentCreateResponse(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public static CommentCreateResponse ofEntity(Comment savedComment) {
        return new CommentCreateResponse(
            savedComment.getUserId()
            , savedComment.getPostId()
            , savedComment.getContent()
        );
    }
}
