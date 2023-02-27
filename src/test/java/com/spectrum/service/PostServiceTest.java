package com.spectrum.service;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.domain.post.Post;
import com.spectrum.repository.PostRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("게시글 서비스 테스트")
@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @DisplayName("정상적인 게시글 생성 요청이 들어온다면 게시글을 생성 성공")
    @Test
    void create_post_success() {
        // given
        String title = "title4";
        String content = "content4";
        Long memberId = 1L;

        // when
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, memberId);
        postService.save(postCreateRequest.convertToPostCreateDto());

        Post findPost = postRepository.findById(1L).orElse(null);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(findPost.getMemberId()).isEqualTo(memberId);
            softly.assertThat(findPost.getContent()).isEqualTo(content);
            softly.assertThat(findPost.getTitle()).isEqualTo(title);
        });
    }
}
