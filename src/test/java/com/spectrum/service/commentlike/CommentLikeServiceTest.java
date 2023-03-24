package com.spectrum.service.commentlike;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.commentlike.dto.CommentLikeResponse;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.commentlike.CommentLikeRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import com.spectrum.service.commentlike.dto.CommentLikeDto;
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
@DisplayName("댓글 좋아요 서비스 테스트")
class CommentLikeServiceTest {


    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Autowired
    CommentLikeService commentLikeService;

    User user;
    Comment comment;
    Comment replies;
    Post post;

    @BeforeEach
    void init() {
        user = userRepository.save(
            new User(1L, "12345678", "username", "email", "imageUrl", Authority.GUEST)
        );
        post = postRepository.save(
            new Post("제목", "게시글", user.getId())
        );
        comment = commentRepository.save(
            new Comment(user.getId(), post.getId(), "댓글입니다.")
        );
        replies = commentRepository.save(
            new Comment(user.getId(), post.getId(), comment.getId(), "대댓글입니다.")
        );
    }

    @AfterEach
    void teardown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentLikeRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적인 댓글 좋아요 요청 시 좋아요 성공")
    void create_comment_like_success() {
        // given
        CommentLikeDto request = new CommentLikeDto(true);

        // when
        CommentLikeResponse response = commentLikeService.save(user.getId(), comment.getId(),
            request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getLikeStatus()).isEqualTo(true);
                softly.assertThat(response.getUserId()).isEqualTo(user.getId());
                softly.assertThat(response.getCommentId()).isEqualTo(comment.getId());
            }
        );
    }

    @Test
    @DisplayName("정상적인 대댓글 좋아요 요청 시 좋아요 성공")
    void create_replies_like_success() {
        // given
        CommentLikeDto request = new CommentLikeDto(true);

        // when
        CommentLikeResponse response = commentLikeService.save(user.getId(), replies.getId(),
            request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getLikeStatus()).isEqualTo(true);
                softly.assertThat(response.getUserId()).isEqualTo(user.getId());
                softly.assertThat(response.getCommentId()).isEqualTo(replies.getId());
            }
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저가 댓글 좋아요 요청시 좋아요 실패")
    void create_comment_like_not_user_failure() {
        // given
        CommentLikeDto request = new CommentLikeDto(true);

        // when & then
        assertThatThrownBy(
            () -> commentLikeService.save(-1L, comment.getId(), request)
        ).isInstanceOf(UserNotFoundException.class);
    }
}
