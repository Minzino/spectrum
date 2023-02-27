package com.spectrum.controller.post.dto;

import lombok.Getter;

@Getter
public class PostCreateRequest {

    private String title;
    private String content;

    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
