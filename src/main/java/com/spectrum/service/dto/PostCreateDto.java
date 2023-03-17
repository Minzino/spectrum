package com.spectrum.service.dto;

import com.spectrum.domain.post.Post;
import lombok.Getter;

@Getter
public class PostCreateDto {
    private final String title;
    private final String content;

    public PostCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post convertToEntity(Long userId) {
        return new Post(title, content, userId);
    }
}
