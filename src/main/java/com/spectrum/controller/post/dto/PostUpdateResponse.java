package com.spectrum.controller.post.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateResponse {

    private String title;
    private String content;
    private Long userId;

    public PostUpdateResponse(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public static PostUpdateResponse ofEntity(Post updatePost) {
        return new PostUpdateResponse(
            updatePost.getTitle()
            , updatePost.getContent()
            , updatePost.getUserId());
    }
}
