package com.spectrum.controller.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDto {

    private String title;
    private String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
