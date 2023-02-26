package com.spectrum.controller.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterResponse {

    private String postId;
    private String content;
    private String title;

    public PostRegisterResponse(String postId, String content, String title) {
        this.postId = postId;
        this.content = content;
        this.title = title;
    }
}
