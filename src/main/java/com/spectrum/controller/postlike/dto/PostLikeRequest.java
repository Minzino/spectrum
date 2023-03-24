package com.spectrum.controller.postlike.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spectrum.service.postlike.dto.PostLikeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostLikeRequest {

    @JsonProperty("like_status")
    private Boolean likeStatus;

    public PostLikeRequest(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public PostLikeDto convertToPostLikeDto() {
        return new PostLikeDto(likeStatus);
    }
}
