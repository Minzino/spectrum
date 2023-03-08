package com.spectrum.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDto {

    private Long postId;
    private String title;
    private String content;

    public PostDto(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

}
