package com.spectrum.service.commentlike.dto;

import com.spectrum.domain.commentlike.CommentLike;
import lombok.Getter;

@Getter
public class CommentLikeDto {

    private final Boolean likeStatus;

    public CommentLikeDto(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public CommentLike convertToEntity(Long userId, Long commentId) {
        return new CommentLike(userId, commentId, likeStatus);
    }
}
