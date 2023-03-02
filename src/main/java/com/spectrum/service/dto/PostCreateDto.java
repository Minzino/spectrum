package com.spectrum.service.dto;

import com.spectrum.domain.post.Post;
import lombok.Getter;

@Getter
public class PostCreateDto {
    private final String title;
    private final String content;
    private final Long memberId;

    public PostCreateDto(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public Post convertToEntity() {
        return new Post(title, content, memberId);
    }
}
