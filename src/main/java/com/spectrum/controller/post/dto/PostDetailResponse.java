package com.spectrum.controller.post.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDetailResponse {

    private Long postId;
    private String title;
    private String content;

    public PostDetailResponse(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public static PostDetailResponse ofEntity(Post post) {
        return new PostDetailResponse(
            post.getId()
            , post.getTitle()
            , post.getContent()
        );
    }
}
