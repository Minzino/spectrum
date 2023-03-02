package com.spectrum.controller.post.dto;

import com.spectrum.service.dto.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequest {

    private Long memberId;
    private String title;
    private String content;

    public PostUpdateRequest(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public PostUpdateDto convertToPostUpdateDto() {
        return new PostUpdateDto(title, content);
    }
}
