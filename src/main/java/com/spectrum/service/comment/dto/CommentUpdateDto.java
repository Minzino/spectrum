package com.spectrum.service.comment.dto;

import lombok.Getter;

@Getter
public class CommentUpdateDto {

    private final String content;

    public CommentUpdateDto(String content) {
        this.content = content;
    }
}
