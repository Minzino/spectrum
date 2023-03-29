package com.spectrum.controller.comment.dto;

import com.spectrum.service.comment.dto.CommentDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListResponse {

    private List<CommentDto> comments;
    public CommentListResponse(List<CommentDto> comments) {
        this.comments = comments;
    }
}
