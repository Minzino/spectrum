package com.spectrum.service.postlike.dto;

import com.spectrum.domain.postlike.PostLike;
import lombok.Getter;

@Getter
public class PostLikeDto {

    private final Boolean likeStatus;

    public PostLikeDto(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public PostLike convertToEntity(Long userId, Long postId) {
        return new PostLike(userId, postId, likeStatus);
    }
}
