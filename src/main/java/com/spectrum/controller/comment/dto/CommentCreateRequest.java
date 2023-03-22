package com.spectrum.controller.comment.dto;

import com.spectrum.service.comment.dto.CommentCreateDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentCreateRequest {

    @NotBlank
    @Size(max = 500)
    private String content;

    public CommentCreateDto convertToCommentCreateDto() {
        return new CommentCreateDto(content);
    }
}
