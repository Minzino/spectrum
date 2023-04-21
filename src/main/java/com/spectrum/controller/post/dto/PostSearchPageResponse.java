package com.spectrum.controller.post.dto;

import com.spectrum.service.post.dto.PostDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSearchPageResponse {

    private List<PostDto> posts;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    public PostSearchPageResponse(List<PostDto> posts, int totalPages, long totalElements, int currentPage) {
        this.posts = posts;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }
}
