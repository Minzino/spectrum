package com.spectrum.controller.comment.dto;

import com.spectrum.service.comment.dto.RepliesDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepliesListResponse {

    private List<RepliesDto> repliesDtos;

    public RepliesListResponse(List<RepliesDto> repliesDtos) {
        this.repliesDtos = repliesDtos;
    }
}
