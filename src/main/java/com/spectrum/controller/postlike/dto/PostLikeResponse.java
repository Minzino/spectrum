package com.spectrum.controller.postlike.dto;

import com.spectrum.domain.postlike.PostLike;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostLikeResponse {

    private Long postLikeId;
    private Long postId;
    private Long userId;
    private Boolean likeStatus;

    public PostLikeResponse(Long postLikeId, Long postId, Long userId, Boolean likeStatus) {
        this.postLikeId = postLikeId;
        this.postId = postId;
        this.userId = userId;
        this.likeStatus = likeStatus;
    }

    public static PostLikeResponse ofEntity(PostLike postLike) {
        return new PostLikeResponse(
            postLike.getId()
            , postLike.getPostId()
            , postLike.getUserId()
            , postLike.getLikeStatus()
        );
    }
}
