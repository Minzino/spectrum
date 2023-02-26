package com.spectrum.controller.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterRequest {

    private String title;
    private String content;

    public PostRegisterRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
