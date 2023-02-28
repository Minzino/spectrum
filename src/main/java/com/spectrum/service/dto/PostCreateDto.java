package com.spectrum.service.dto;

import com.spectrum.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateDto {
    private String title;
    private String content;
    private Long memberId;

    public PostCreateDto(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public Post convertToEntity() {
        return new Post(title, content, memberId);
    }
}
