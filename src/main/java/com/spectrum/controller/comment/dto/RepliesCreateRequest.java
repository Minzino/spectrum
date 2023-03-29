package com.spectrum.controller.comment.dto;

import com.spectrum.service.comment.dto.RepliesCreateDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepliesCreateRequest {

    @NotBlank
    @Size(max = 500)
    private String content;

    public RepliesCreateDto convertToRepliesDto() {
        return new RepliesCreateDto(content);
    }
}
