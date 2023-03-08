package com.spectrum.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostListResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.repository.PostRepository;
import com.spectrum.service.dto.PostCreateDto;
import com.spectrum.service.dto.PostUpdateDto;
import java.util.Optional;
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

    Post savePost1;
    Post savePost2;
    private static final Long MEMBER_ID = 1L;
    private static final Long FAKE_ID = -1L;

    @BeforeEach
    void init() {
        savePost1 = postRepository.save(new Post("title", "content", MEMBER_ID));
        savePost2 = postRepository.save(new Post("title", "content", MEMBER_ID));
    }

    @DisplayName("정상적인 게시글 생성 요청이 들어온다면 게시글 생성 성공")
    @Test
    void create_post_success() {
        // given
        String title = "title1";
        String content = "content1";

        PostCreateDto postCreateDto = new PostCreateDto(title, content);

        // when
        PostCreateResponse response = postService.save(MEMBER_ID, postCreateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getMemberId()).isEqualTo(MEMBER_ID);
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
        PostUpdateResponse response = postService.update(MEMBER_ID, savePost1.getId(), postUpdateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getMemberId()).isEqualTo(MEMBER_ID);
            softly.assertThat(response.getContent()).isEqualTo(postUpdateDto.getContent());
            softly.assertThat(response.getTitle()).isEqualTo(postUpdateDto.getTitle());
        });
    }

    @DisplayName("존재하지 않는 게시글을 수정하는 경우 예외 발생")
    @Test
    void update_post_failure() {
        // given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        PostUpdateDto postUpdateDto = new PostUpdateDto(updateContent, updateTitle);

        // when & then
        assertThatThrownBy(
            () -> postService.update(MEMBER_ID, FAKE_ID, postUpdateDto))
            .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적인 게시글 삭제 요청의 경우 삭제 성공")
    @Test
    void delete_post_success() {
        // given
        Long savePostId = savePost1.getId();

        // when
        postService.delete(MEMBER_ID, savePostId);
        Optional<Post> deletePost = postRepository.findById(savePostId);

        // then
        assertThat(deletePost).isNotPresent();
    }

    @DisplayName("존재하지 않는 게시글 삭제 요청의 경우 예외 발생")
    @Test
    void delete_post_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(MEMBER_ID, FAKE_ID))
            .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적인 게시글 단건 조회 요청시 조회 성공")
    @Test
    void get_post_by_id_success() {
        // given
        Long postId = savePost1.getId();

        // when
        PostDetailResponse post = postService.findPostById(postId);

        // then
        assertThat(post.getPostId()).isEqualTo(postId);
        assertThat(post.getTitle()).isEqualTo(savePost1.getTitle());
        assertThat(post.getContent()).isEqualTo(savePost1.getContent());
    }

    @DisplayName("존재하지 않는 게시글 조회 요청의 경우 예외 발생")
    @Test
    void get_post_by_id_failure() {
        // when & then
        assertThatThrownBy(() -> postService.findPostById(FAKE_ID))
            .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적인 게시글 전체 조회시 전체 조회 성공")
    @Test
    void get_all_post_success() {
        // given & when
        PostListResponse postList = postService.findAll();
        // then
        assertThat(postList.getPosts().size()).isEqualTo(postRepository.count());
    }
}
