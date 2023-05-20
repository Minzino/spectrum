package com.spectrum.controller.post.dto;

import com.spectrum.service.post.dto.PostDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostPageResponse {

    private List<PostDto> posts;
    private int numberOfElements;
    private Long lastCursor;

    public PostPageResponse(List<PostDto> posts, int numberOfElements, Long lastCursor) {
        this.posts = posts;
        this.numberOfElements = numberOfElements;
        this.lastCursor = lastCursor;
    }
}
