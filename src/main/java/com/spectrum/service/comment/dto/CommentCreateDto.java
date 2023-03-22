package com.spectrum.service.comment.dto;

import com.spectrum.domain.comment.Comment;

public class CommentCreateDto {

    private final String content;

    public CommentCreateDto(String content) {
        this.content = content;
    }

    public Comment convertToEntity(Long userId, Long postId) {
        return new Comment(userId, postId, content);
    }
}
