package com.spectrum.controller.post.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateResponse {

    private Long memberId;
    private String title;
    private String content;

    public PostCreateResponse(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public static PostCreateResponse ofEntity(Post savePost) {
        return new PostCreateResponse(
            savePost.getMemberId()
            , savePost.getTitle()
            , savePost.getContent());
    }
}
