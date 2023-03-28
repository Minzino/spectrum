package com.spectrum.controller.postlike;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.common.auth.provider.JwtProvider;
import com.spectrum.controller.postlike.dto.PostLikeRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class PostLikeControllerTest extends InitIntegrationDocsTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;

    User user;
    Post post;
    String validToken;

    @BeforeAll
    void init() {
        user = userRepository.save(
            new User(1L, "12345678", "username", "email", "imageUrl", Authority.GUEST)
        );
        post = postRepository.save(
            new Post("제목", "게시글", user.getId())
        );
        validToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
    }

    @Test
    @DisplayName("정상적인 게시글 좋아요 요청 시 게시글 좋아요 성공")
    void create_post_like_success() {
        PostLikeRequest request = new PostLikeRequest(true);

        given(this.spec)
            .filter(
                document("postlike-create",
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
            .post("/api/posts/{postId}/likes", post.getId())

            .then()
            .statusCode(HttpStatus.CREATED.value());
    }
}
