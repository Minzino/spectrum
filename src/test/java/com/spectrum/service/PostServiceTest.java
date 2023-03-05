package com.spectrum.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostUpdateDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
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

    Post savePost;
    Long memberId = 1L;

    @BeforeEach
    void init() {
        savePost = postRepository.save(new Post("title", "content", 1L));
    }

    @DisplayName("정상적인 게시글 생성 요청이 들어온다면 게시글 생성 성공")
    @Test
    void create_post_success() {
        // given
        String title = "title1";
        String content = "content1";

        PostCreateDto postCreateDto = new PostCreateDto(title, content);

        // when
        PostCreateResponse response = postService.save(memberId, postCreateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getMemberId()).isEqualTo(memberId);
            softly.assertThat(response.getContent()).isEqualTo(postCreateDto.getContent());
            softly.assertThat(response.getTitle()).isEqualTo(postCreateDto.getTitle());
        });
    }

    @DisplayName("정상적인 게시글 수정 요청이 들어온다면 게시글 변경 성공")
    @Test
    void update_post_success() {
        // given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        PostUpdateDto postUpdateDto = new PostUpdateDto(updateContent, updateTitle);

        // when
        PostUpdateResponse response = postService.update(memberId, savePost.getId(), postUpdateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getMemberId()).isEqualTo(memberId);
            softly.assertThat(response.getContent()).isEqualTo(postUpdateDto.getContent());
            softly.assertThat(response.getTitle()).isEqualTo(postUpdateDto.getTitle());
        });
    }

    @DisplayName("존재하지 않는 게시글을 수정하는 경우 예외 발생")
    @Test
    void update_post_failure() {
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        Long fakePostId = -1L;

        PostUpdateDto postUpdateDto = new PostUpdateDto(updateContent, updateTitle);

        assertThatThrownBy(
            () -> postService.update(memberId, fakePostId, postUpdateDto))
            .isInstanceOf(PostNotFoundException.class);
    }
}
