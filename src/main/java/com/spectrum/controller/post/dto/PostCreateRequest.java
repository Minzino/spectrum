package com.spectrum.controller.post.dto;

import com.spectrum.service.dto.PostCreateDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    @Size(max = 10_000)
    private String content;

    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostCreateDto convertToPostCreateDto() {
        return new PostCreateDto(title, content);
    }
}
