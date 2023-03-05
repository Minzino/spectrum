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
    private Long memberId;

    public PostUpdateResponse(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public static PostUpdateResponse ofEntity(Post updatePost) {
        return new PostUpdateResponse(
            updatePost.getTitle()
            , updatePost.getContent()
            , updatePost.getMemberId());
    }
}
