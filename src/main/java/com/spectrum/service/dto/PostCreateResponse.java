package com.spectrum.service.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateResponse {

    private Long memberId;

    public PostCreateResponse(Post savedPost) {
        this.memberId = savedPost.getMemberId();
    }
}
