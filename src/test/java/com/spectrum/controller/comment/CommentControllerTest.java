package com.spectrum.controller.comment;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.auth.provider.JwtProvider;
import com.spectrum.controller.comment.dto.CommentCreateRequest;
import com.spectrum.controller.comment.dto.CommentUpdateRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class CommentControllerTest extends InitIntegrationDocsTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;
    User user;
    Post post;
    Comment comment1;
    Comment comment2;
    Comment comment3;
    Comment replies1;
    Comment replies2;
    Comment replies3;
    String validToken;

    @BeforeAll
    void init() {
        user = userRepository.save(
            new User(1L, "123456789", "username", "email", "imageUrl", Authority.GUEST)
        );
        post = postRepository.save(
            new Post("제목", "게시글", user.getId())
        );
        comment1 = commentRepository.save(
            new Comment(user.getId(), post.getId(), "content")
        );
        comment2 = commentRepository.save(
            new Comment(user.getId(), post.getId(), "content")
        );
        comment3 = commentRepository.save(
            new Comment(user.getId(), post.getId(), "content")
        );
        replies1 = commentRepository.save(
            new Comment(user.getId(), post.getId(), comment2.getId(), "re-content")
        );
        replies2 = commentRepository.save(
            new Comment(user.getId(), post.getId(), comment2.getId(), "re-content")
        );
        replies3 = commentRepository.save(
            new Comment(user.getId(), post.getId(), comment2.getId(), "re-content")
        );
        validToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
    }

    @AfterEach
    void teardown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성 요청이 정상적인 경우라면 댓글 생성 성공")
    void create_comment_success() {
        CommentCreateRequest request = new CommentCreateRequest("댓글 내용");

        given(this.spec)
            .filter(
                document("comment-create",
                    requestFields(
                        fieldWithPath("content").description("댓글 내용").type(JsonFieldType.STRING)
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)

            .when()
            .post("/api/posts/{postId}/comments", post.getId())

            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("댓글 수정 요청이 정상적인 경우라면 댓글 수정 성공")
    void update_comment_success() {
        CommentUpdateRequest request = new CommentUpdateRequest("수정된 댓글입니다.");

        given(this.spec)
            .filter(
                document("comment-update",
                    requestFields(
                        fieldWithPath("content").description("변경할 댓글 내용").type(JsonFieldType.STRING)
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)
            .body(request)

            .when()
            .put("/api/posts/{postId}/comments/{commentId}", post.getId(), comment2.getId())

            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("댓글 삭제 요청이 정상적인 경우라면 댓글 삭제 성공")
    void delete_comment_success() {

        given(this.spec)
            .filter(
                document("comment-delete",
                    pathParameters(parameterWithName("commentId").description("삭제할 댓글의 ID"))
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)

            .when()
            .delete("/api/comments/{commentId}", comment1.getId())

            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("댓글 전체 조회 요청이 정상적인 경우 댓글 전체 조회 성공")
    void findAll_comment_success() {
        given(this.spec)
            .filter(
                document("comment-findAll")
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)

            .when()
            .get("/api/posts/{postId}/comments", post.getId())

            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("대댓글 생성 요청이 정상적인 경우라면 댓글 생성 성공")
    void create_reply_success() {
        CommentCreateRequest request = new CommentCreateRequest("대댓글 입니다.");

        given(this.spec)
            .filter(
                document("reply-create",
                    requestFields(
                        fieldWithPath("content").description("대댓글 내용").type(JsonFieldType.STRING)
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)

            .when()
            .post("/api/comments/{commentId}/replies", comment2.getId())

            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("대댓글 전체 조회 요청이 정상적인 경우 대댓글 전체 조회 성공")
    void findAll_reply_success() {
        given(this.spec)
            .filter(
                document("reply-findAll")
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)

            .when()
            .get("/api/comments/{commentId}/replies", comment2.getId())

            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
