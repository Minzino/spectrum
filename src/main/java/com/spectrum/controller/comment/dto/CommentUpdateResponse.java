package com.spectrum.controller.comment.dto;

import com.spectrum.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUpdateResponse {

    private Long userId;
    private Long postId;
    private Long commentId;
    private String content;

    public CommentUpdateResponse(Long userId, Long postId, Long commentId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.content = content;
    }

    public static CommentUpdateResponse ofEntity(Comment comment) {
        return new CommentUpdateResponse(
            comment.getUserId()
            , comment.getPostId()
            , comment.getId()
            , comment.getContent()
        );
    }
}
