package com.spectrum.controller.comment.dto;

import com.spectrum.service.comment.dto.CommentUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentUpdateRequest {

    private String content;

    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    public CommentUpdateDto convertToCommentUpdateDto() {
        return new CommentUpdateDto(content);
    }
}
