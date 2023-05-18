package com.spectrum.service.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.post.dto.PostCreateResponse;
import com.spectrum.controller.post.dto.PostDetailResponse;
import com.spectrum.controller.post.dto.PostPageResponse;
import com.spectrum.controller.post.dto.PostUpdateResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.post.NotAuthorException;
import com.spectrum.exception.post.PostNotFoundException;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.service.post.dto.PostCreateDto;
import com.spectrum.service.post.dto.PostUpdateDto;
import com.spectrum.setup.IntegerationTest;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DisplayName("게시글 서비스 테스트")
class PostServiceTest extends IntegerationTest {

    private static final Long FAKE_ID = -1L;
    private static final Long USER1_ID = 1L;
    private static final Long USER2_ID = 2L;
    private static final Long POST_ID = 1L;

    @Autowired
    PostService postService;

    Post savePost1;
    Post savePost2;
    Post savePost3;
    User user1;
    User user2;

    @BeforeEach
    void init() {
        user1 = userRepository.save(
            new User(1L, "12345678", "username1", "email1", "imageUrl1", Authority.GUEST)
        );
        user2 = userRepository.save(
            new User(2L, "45678", "username2", "email2", "imageUrl2", Authority.GUEST)
        );
        savePost1 = postRepository.save(new Post("title1", "content1", USER1_ID));
        savePost2 = postRepository.save(new Post("title2", "content2", USER2_ID));
        savePost3 = postRepository.save(new Post("title3", "content3", USER1_ID));
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
        PostUpdateResponse response = postService.update(USER1_ID, POST_ID,
            postUpdateDto);

        // then
        SoftAssertions.assertSoftly(softly -> {
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
            softly.assertThat(post.getTitle()).isEqualTo("title1");
            softly.assertThat(post.getContent()).isEqualTo("content1");
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

    @DisplayName("정상적인 게시글 페이지 조회시 페이지 조회 성공")
    @Test
    void get_page_post_success() {
        // given
        int size = 10;
        Long cursor = 4L;
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        // when
        PostPageResponse page = postService.findByPage(cursor, pageable);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(page.getPosts().size()).isEqualTo(3);
            softly.assertThat(page.getNumberOfElements()).isEqualTo(3);
        });
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
            () -> postService.update(FAKE_ID, POST_ID, postUpdateDto))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("존재하지 않는 회원이 게시글 삭제 요청 시 예외 발생")
    @Test
    void delete_post_not_user_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(FAKE_ID, POST_ID))
            .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("글 작성자가 아닌 회원이 삭제 요청 시 예외 발생")
    @Test
    void delete_post_not_author_failure() {
        // when & then
        assertThatThrownBy(
            () -> postService.delete(USER2_ID, POST_ID))
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
            () -> postService.update(USER2_ID, POST_ID, postUpdateDto))
            .isInstanceOf(NotAuthorException.class);
    }

    @DisplayName("정상적인 제목,내용 검색 요청시 검색 성공")
    @Test
    void get_search_page_post_success() {
        // given
        int size = 10;
        Long cursor = 4L;
        String searchType = "title";
        String searchValue = "title";
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        // when
        PostPageResponse page = postService.searchPosts(
            cursor, searchType, searchValue, pageable
        );
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(page.getPosts().size()).isEqualTo(3);
            softly.assertThat(page.getNumberOfElements()).isEqualTo(3);
        });
    }
}
