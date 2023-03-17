package com.spectrum.controller.post.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateResponse {

    private Long postId;
    private Long userId;
    private String title;
    private String content;

    public PostCreateResponse(Long postId, Long userId, String title, String content) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public static PostCreateResponse ofEntity(Post savePost) {
        return new PostCreateResponse(
            savePost.getId()
            , savePost.getUserId()
            , savePost.getTitle()
            , savePost.getContent());
    }
}
