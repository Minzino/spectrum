package com.spectrum.controller.comment.dto;

import com.spectrum.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepliesCreateResponse {

    private Long parentId;
    private Long userId;
    private Long postId;
    private String content;

    private RepliesCreateResponse(Long parentId, Long userId, Long postId,
        String content) {
        this.parentId = parentId;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public static RepliesCreateResponse ofEntity(Comment replies) {
        return new RepliesCreateResponse(
            replies.getParentId()
            , replies.getUserId()
            , replies.getPostId()
            , replies.getContent()
        );
    }
}
