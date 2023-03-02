package com.spectrum.controller.post.dto;

import com.spectrum.service.dto.PostCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

    private Long memberId;
    private String title;
    private String content;

    public PostCreateRequest(String title, String content, Long memberId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public PostCreateDto convertToPostCreateDto() {
        return new PostCreateDto(title, content, memberId);
    }
}
