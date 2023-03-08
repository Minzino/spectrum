package com.spectrum.controller.post.dto;

import com.spectrum.service.dto.PostDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostListResponse {

    private List<PostDto> posts;

    public PostListResponse(List<PostDto> posts) {
        this.posts = posts;
    }
}
