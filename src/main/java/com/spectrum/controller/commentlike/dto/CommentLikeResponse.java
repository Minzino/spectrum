package com.spectrum.controller.commentlike.dto;

import com.spectrum.domain.commentlike.CommentLike;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeResponse {

    private Long commentLikeId;
    private Long commentId;
    private Long userId;
    private Boolean likeStatus;

    public CommentLikeResponse(Long commentLikeId, Long commentId, Long userId,
        Boolean likeStatus) {
        this.commentLikeId = commentLikeId;
        this.commentId = commentId;
        this.userId = userId;
        this.likeStatus = likeStatus;
    }

    public static CommentLikeResponse ofEntity(CommentLike commentLike) {
        return new CommentLikeResponse(
            commentLike.getId()
            , commentLike.getCommentId()
            , commentLike.getUserId()
            , commentLike.getLikeStatus()
        );
    }
}
