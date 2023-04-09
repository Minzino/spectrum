package com.spectrum.service.postlike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.postlike.dto.PostLikeResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.service.postlike.dto.PostLikeDto;
import com.spectrum.setup.IntegerationTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("게시글 좋아요 서비스 테스트")
class PostLikeServiceTest extends IntegerationTest {

    private final static Long USER_ID = 1L;
    private final static Long FAKE_ID = -1L;
    private final static Long POST_ID = 1L;
    @Autowired
    PostLikeService postLikeService;

    User user;
    Post post;

    @BeforeEach
    void init() {
        user = userRepository.save(
            new User(1L, "12345678", "username", "email", "imageUrl", Authority.GUEST)
        );
        post = postRepository.save(
            new Post("제목", "게시글", USER_ID)
        );
    }

    @Test
    @DisplayName("정상적인 게시글 좋아요 요청 시 좋아요 성공")
    void create_post_like_success() {
        // given
        PostLikeDto request = new PostLikeDto(true);

        // when
        PostLikeResponse response = postLikeService.save(USER_ID, POST_ID, request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getLikeStatus()).isEqualTo(true);
                softly.assertThat(response.getUserId()).isEqualTo(USER_ID);
                softly.assertThat(response.getPostId()).isEqualTo(POST_ID);
            }
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저가 게시글 좋아요 요청시 좋아요 실패")
    void create_post_like_not_user_failure() {
        // given
        PostLikeDto request = new PostLikeDto(true);

        // when & then
        assertThatThrownBy(
            () -> postLikeService.save(FAKE_ID, POST_ID, request)
        ).isInstanceOf(UserNotFoundException.class);
    }
}
