package com.spectrum.controller.commentlike.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spectrum.service.commentlike.dto.CommentLikeDto;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeRequest {

    @JsonProperty("like_status")
    @NotNull
    private Boolean likeStatus;

    public CommentLikeRequest(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public CommentLikeDto convertToCommentLikeDto() {
        return new CommentLikeDto(likeStatus);
    }
}
