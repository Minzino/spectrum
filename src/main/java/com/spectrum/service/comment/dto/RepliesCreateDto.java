package com.spectrum.service.comment.dto;

import com.spectrum.domain.comment.Comment;
import lombok.Getter;

@Getter
public class RepliesCreateDto {

    private final String content;

    public RepliesCreateDto(String content) {
        this.content = content;
    }

    public Comment convertToEntity(Long postId, Long parentId, Long userId) {
        return new Comment(
            userId
            , postId
            , parentId
            , content
        );
    }
}
