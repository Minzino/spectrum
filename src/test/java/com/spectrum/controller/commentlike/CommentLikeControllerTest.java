package com.spectrum.controller.commentlike;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.auth.provider.JwtProvider;
import com.spectrum.controller.commentlike.dto.CommentLikeRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import com.spectrum.domain.comment.Comment;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.repository.comment.CommentRepository;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;


class CommentLikeControllerTest extends InitIntegrationDocsTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;

    String validToken;

    User user;
    Comment comment;

    @BeforeAll
    void init() {
        user = userRepository.save(
            new User(1L, "12345678", "username", "email", "imageUrl", Authority.GUEST)
        );
        comment = commentRepository.save(
            new Comment(user.getId(), 1L, "댓글입니다.")
        );
        validToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
    }

    @Test
    @DisplayName("정상적인 댓글 좋아요 요청 시 댓글 좋아요 성공")
    void create_comment_like_success() {
        CommentLikeRequest request = new CommentLikeRequest(true);

        given(this.spec)
            .filter(
                document("commentlike-create",
                    requestFields(
                        fieldWithPath("like_status").description("좋아요 상태")
                            .type(JsonFieldType.BOOLEAN)
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)

            .when()
            .post("/api/comments/{commentId}/likes", comment.getId())

            .then()
            .statusCode(HttpStatus.CREATED.value());
    }
}
