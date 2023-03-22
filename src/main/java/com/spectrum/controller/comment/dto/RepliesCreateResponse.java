package com.spectrum.controller.comment.dto;

import com.spectrum.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepliesCreateResponse {

    private Long commentId;

    private RepliesCreateResponse(Long commentId) {
        this.commentId = commentId;
    }

    public static RepliesCreateResponse ofEntity(Comment replies) {
        return new RepliesCreateResponse(
            replies.getId());
    }
}
