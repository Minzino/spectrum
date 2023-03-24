package com.spectrum.service.postlike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.postlike.dto.PostLikeResponse;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.postlike.PostLikeRepository;
import com.spectrum.repository.user.UserRepository;
import com.spectrum.service.postlike.dto.PostLikeDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("게시글 좋아요 서비스 테스트")
class PostLikeServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
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
            new Post("제목", "게시글", user.getId())
        );
    }

    @AfterEach
    void teardown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        postLikeRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적인 게시글 좋아요 요청 시 좋아요 성공")
    void create_post_like_success() {
        // given
        PostLikeDto request = new PostLikeDto(true);

        // when
        PostLikeResponse response = postLikeService.save(user.getId(), post.getId(), request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getPostLikeId()).isEqualTo(1L);
                softly.assertThat(response.getLikeStatus()).isEqualTo(true);
                softly.assertThat(response.getUserId()).isEqualTo(user.getId());
                softly.assertThat(response.getPostId()).isEqualTo(post.getId());
            }
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저가 게시글 좋아요 요청시 좋아요 실패")
    void create_post_like_not_user_failure() {
        // given
        PostLikeDto request = new PostLikeDto(true);

        assertThatThrownBy(
            () -> postLikeService.save(-1L, post.getId(), request)
        ).isInstanceOf(UserNotFoundException.class);
    }
}
