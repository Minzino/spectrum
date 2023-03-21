package com.spectrum.service.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateDto {

    private final String content;
    private final String title;

    public PostUpdateDto(String content, String title) {
        this.content = content;
        this.title = title;
    }
}
