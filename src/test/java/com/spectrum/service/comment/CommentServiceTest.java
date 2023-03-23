package com.spectrum.service.comment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spectrum.controller.comment.dto.CommentCreateResponse;
import com.spectrum.controller.comment.dto.CommentListResponse;
import com.spectrum.controller.comment.dto.CommentUpdateResponse;
import com.spectrum.controller.comment.dto.RepliesCreateResponse;
import com.spectrum.controller.comment.dto.RepliesListResponse;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.exception.comment.NotParentException;
import com.spectrum.exception.post.NotAuthorException;
import com.spectrum.exception.user.UserNotFoundException;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import com.spectrum.service.comment.dto.CommentCreateDto;
import com.spectrum.service.comment.dto.CommentUpdateDto;
import com.spectrum.service.comment.dto.RepliesCreateDto;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@DisplayName("댓글 서비스 테스트")
@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {

    private static final Long USER1_ID = 1L;
    private static final Long USER2_ID = 2L;
    private static final Long FAKE_ID = -1L;

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    User user1;
    User user2;
    Post post;
    Comment comment;
    Comment recomment;

    @BeforeEach
    void init() {
        user1 = userRepository.save(
            new User(USER1_ID, "12345678", "username1", "email1", "imageUrl1", Authority.GUEST)
        );
        user2 = userRepository.save(
            new User(USER2_ID, "3456789", "username2", "email2", "imageUrl2", Authority.GUEST)
        );
        post = postRepository.save(
            new Post("제목", "게시글", USER1_ID)
        );
        comment = commentRepository.save(
            new Comment(USER1_ID, post.getId(), "댓글입니다.")
        );
        recomment = commentRepository.save(
            new Comment(USER1_ID, post.getId(), comment.getId(), "대댓글입니다.")
        );
    }

    @AfterEach
    void teardown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적인 댓글 생성 요청 시 댓글 생성 성공")
    void create_comment_success() {
        // given
        CommentCreateDto request = new CommentCreateDto("댓글입니다.");

        // when
        CommentCreateResponse response = commentService.save(user1.getId(), post.getId(), request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getUserId()).isEqualTo(user1.getId());
                softly.assertThat(response.getPostId()).isEqualTo(post.getId());
                softly.assertThat(response.getContent()).isEqualTo("댓글입니다.");
            }
        );
    }

    @Test
    @DisplayName("정상적인 댓글 수정 요청 시 댓글 수정 성공")
    void update_comment_success() {
        // given
        CommentUpdateDto request = new CommentUpdateDto("수정된 댓글입니다.");

        // when
        CommentUpdateResponse response = commentService.update(USER1_ID, post.getId(),
            comment.getId(), request);

        //then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getCommentId()).isEqualTo(comment.getId());
                softly.assertThat(response.getContent()).isEqualTo("수정된 댓글입니다.");
                softly.assertThat(response.getUserId()).isEqualTo(USER1_ID);
                softly.assertThat(response.getPostId()).isEqualTo(post.getId());
            }
        );
    }

    @Test
    @DisplayName("정상적인 댓글 삭제 요청 시 댓글 삭제 성공")
    void delete_comment_success() {
        // given & when
        commentService.delete(user1.getId(), comment.getId());
        Optional<Comment> deleteComment = commentRepository.findById(comment.getId());

        //then
        Assertions.assertThat(deleteComment).isNotPresent();
    }

    @Test
    @DisplayName("정상적인 댓글 조회 요청 시 댓글 조회 성공")
    void findAll_comment_success() {
        // given & when
        CommentListResponse response = commentService.findAll(post.getId());

        // then
        Assertions.assertThat(response.getComments().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("정상적인 대댓글 생성 요청 시 대댓글 생성 성공")
    void create_replies_success() {
        // given
        RepliesCreateDto request = new RepliesCreateDto("대댓글 입니다.");

        // when
        RepliesCreateResponse response = commentService.saveReplies(user1.getId(), comment.getId(),
            request);

        // then
        SoftAssertions.assertSoftly(
            softly -> {
                softly.assertThat(response.getParentId()).isEqualTo(comment.getId());
                softly.assertThat(response.getUserId()).isEqualTo(USER1_ID);
                softly.assertThat(response.getContent()).isEqualTo("대댓글 입니다.");
            }
        );
    }

    @Test
    @DisplayName("정상적인 대댓글 조회 요청 시 대댓글 조회 성공")
    void find_all_replies_success() {
        // given & when
        RepliesListResponse allReplies = commentService.findAllReplies(comment.getId());

        // then
        Assertions.assertThat(allReplies.getRepliesDtos().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 작성자가 아닌 회원이 수정 요청 시 댓글 수정 실패")
    void update_comment_not_author_failure() {
        // given
        CommentUpdateDto request = new CommentUpdateDto("수정된 댓글입니다.");

        // when & then
        assertThatThrownBy(
            () -> commentService.update(user2.getId(), post.getId(), comment.getId(), request)
        ).isInstanceOf(NotAuthorException.class);
    }

    @Test
    @DisplayName("댓글 작성자가 아닌 회원이 삭제 요청 시 댓글 삭제 실패")
    void delete_comment_not_author_failure() {
        // when & then
        assertThatThrownBy(
            () -> commentService.delete(user2.getId(), comment.getId())
        ).isInstanceOf(NotAuthorException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원이 댓글 생성 요청 시 댓글 생성 실패")
    void create_comment_not_user_failure() {
        // given
        CommentCreateDto request = new CommentCreateDto("댓글입니다.");

        // when & then
        assertThatThrownBy(
            () -> commentService.save(FAKE_ID, post.getId(), request)
        ).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원이 댓글 수정 요청 시 댓글 수정 실패")
    void update_comment_not_user_failure() {
        // given
        CommentUpdateDto request = new CommentUpdateDto("수정된 댓글입니다.");

        // when & then
        assertThatThrownBy(
            () -> commentService.update(FAKE_ID, post.getId(), comment.getId(), request)
        ).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("대댓글에 대댓글 생성 요청 시 대댓글 생성 요청 실패")
    void fail_to_create_reply_for_reply() {
        // given
        RepliesCreateDto request = new RepliesCreateDto("대댓글 입니다.");

        // when & then
        assertThatThrownBy(
            () -> commentService.saveReplies(USER1_ID, recomment.getId(), request)
        ).isInstanceOf(NotParentException.class);
    }
}
