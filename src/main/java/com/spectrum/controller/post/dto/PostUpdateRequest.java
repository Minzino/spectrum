package com.spectrum.controller.post.dto;

import com.spectrum.service.post.dto.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequest {

    private String title;
    private String content;

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostUpdateDto convertToPostUpdateDto() {
        return new PostUpdateDto(title, content);
    }
}
