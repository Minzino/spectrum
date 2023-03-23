package com.spectrum.service.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostListResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.post.NotAuthorException;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import com.spectrum.service.post.dto.PostCreateDto;
import com.spectrum.service.post.dto.PostUpdateDto;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("게시글 서비스 테스트")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    Post savePost1;
    Post savePost2;
    User user1;
    User user2;
    private static final Long USER1_ID = 1L;
    private static final Long USER2_ID = 2L;
    private static final Long FAKE_ID = -1L;

    @BeforeAll
    void init() {
        user1 = userRepository.save(
            new User(USER1_ID, "12345678", "username1", "email1", "imageUrl1", Authority.GUEST)
        );
        user2 = userRepository.save(
            new User(USER2_ID, "45678", "username2", "email2", "imageUrl2", Authority.GUEST)
        );
        savePost1 = postRepository.save(new Post("title", "content", USER1_ID));
        savePost2 = postRepository.save(new Post("title", "content", USER2_ID));
    }

    @DisplayName("정상적인 게시글 생성 요청이 들어온다면 게시글 생성 성공")
    @Test
    void create_post_success() {
        // given
        String title = "title";
        String content = "content";

        PostCreateDto postCreateDto = new PostCreateDto(title, content);

        // when
        PostCreateResponse response = postService.save(USER1_ID, postCreateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getUserId()).isEqualTo(1L);
            softly.assertThat(response.getTitle()).isEqualTo("title");
            softly.assertThat(response.getContent()).isEqualTo("content");
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
        PostUpdateResponse response = postService.update(USER1_ID, savePost1.getId(),
            postUpdateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getUserId()).isEqualTo(1L);
            softly.assertThat(response.getContent()).isEqualTo("updateContent");
            softly.assertThat(response.getTitle()).isEqualTo("updateTitle");
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
            () -> postService.update(USER1_ID, FAKE_ID, postUpdateDto))
            .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적인 게시글 삭제 요청의 경우 삭제 성공")
    @Test
    void delete_post_success() {
        // given
        Long savePostId = savePost1.getId();

        // when
        postService.delete(USER1_ID, savePostId);
        Optional<Post> deletePost = postRepository.findById(savePostId);

        // then
        assertThat(deletePost).isNotPresent();
    }

    @DisplayName("존재하지 않는 게시글 삭제 요청의 경우 예외 발생")
    @Test
    void delete_post_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(USER1_ID, FAKE_ID))
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
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(post.getPostId()).isEqualTo(1L);
            softly.assertThat(post.getTitle()).isEqualTo("title");
            softly.assertThat(post.getContent()).isEqualTo("content");
        });
    }

    @DisplayName("존재하지 않는 게시글 조회 요청의 경우 예외 발생")
    @Test
    void get_post_by_id_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.findPostById(FAKE_ID))
            .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적인 게시글 전체 조회시 전체 조회 성공")
    @Test
    void get_all_post_success() {
        // given & when
        PostListResponse postList = postService.findAll();
        // then
        assertThat(postList.getPosts().size()).isEqualTo(2);
    }

    @DisplayName("존재하지 않는 회원이 게시글 생성 요청 시 예외 발생")
    @Test
    void create_post_not_user_failure() {
        // given
        String title = "title1";
        String content = "content1";

        PostCreateDto postCreateDto = new PostCreateDto(title, content);

        // when & then
        assertThatThrownBy(
            () -> postService.save(FAKE_ID, postCreateDto)
        ).isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("존재하지 않는 회원이 게시글 수정 요청 시 예외 발생")
    @Test
    void update_post_not_user_failure() {
        // given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        PostUpdateDto postUpdateDto = new PostUpdateDto(updateContent, updateTitle);
        // when & then
        assertThatThrownBy(
            () -> postService.update(FAKE_ID, savePost1.getId(), postUpdateDto))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("존재하지 않는 회원이 게시글 삭제 요청 시 예외 발생")
    @Test
    void delete_post_not_user_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(FAKE_ID, savePost1.getId()))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("글 작성자가 아닌 회원이 삭제 요청 시 예외 발생")
    @Test
    void delete_post_not_author_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(savePost2.getUserId(), savePost1.getId()))
            .isInstanceOf(NotAuthorException.class);
    }

    @DisplayName("글 작성자가 아닌 회원이 수정 요청 시 예외 발생")
    @Test
    void update_post_not_author_failure() {
        // given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        PostUpdateDto postUpdateDto = new PostUpdateDto(updateContent, updateTitle);
        // when & then
        assertThatThrownBy(
            () -> postService.update(savePost2.getUserId(), savePost1.getId(), postUpdateDto))
            .isInstanceOf(NotAuthorException.class);
    }
}
