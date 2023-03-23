package com.spectrum.controller.post;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.auth.provider.JwtProvider;
import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostUpdateRequest;
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


public class PostControllerTest extends InitIntegrationDocsTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    Post savePost1;
    Post savePost2;
    User user;
    String validToken;

    @BeforeAll
    void init() {
        user = userRepository.save(
            new User(1L, "123456789", "username", "email", "imageUrl", Authority.GUEST)
        );
        savePost1 = postRepository.save(new Post("title1", "content1", user.getId()));
        savePost2 = postRepository.save(new Post("title2", "content2", user.getId()));
        validToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
    }

    @Test
    @DisplayName("게시글 생성 요청이 정상적인 경우라면 게시글 생성 성공")
    void create_post_success() {
        PostCreateRequest request = new PostCreateRequest("title", "content");

        given(this.spec)
            .filter(
                document("post-create",
                    requestFields(
                        fieldWithPath("title").description("게시글 제목").type(JsonFieldType.STRING),
                        fieldWithPath("content").description("게시글 내용").type(JsonFieldType.STRING)
                    )
                )
            )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .header("Authorization", "Bearer " + validToken)

            .when()
            .post("/api/posts")

            .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("게시글 수정 요청이 정상적인 경우라면 게시글 변경 성공")
    void update_post_success() {
        PostUpdateRequest request = new PostUpdateRequest("update title", "update content");

        given(this.spec)
            .filter(
                document("post-update",
                    requestFields(
                        fieldWithPath("title").description("변경할 제목").type(JsonFieldType.STRING),
                        fieldWithPath("content").description("변경할 내용").type(JsonFieldType.STRING)
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)
            .body(request)
        .when()
            .put("/api/posts/{postId}", savePost2.getId())
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("게시글 삭제 요청이 정상적인 경우라면 게시글 삭제 성공")
    void delete_post_success() {

        given(this.spec)
            .filter(
                document("post-delete",
                    pathParameters(parameterWithName("postId").description("삭제할 게시글의 ID"))
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer " + validToken)
        .when()
            .delete("/api/posts/{postId}", savePost1.getId())
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("게시글 단건 조회 요청이 정상적인 경우 게시글 단건 조회 성공")
    void find_post_success() {
        given(this.spec)
            .filter(
                document("post-find",
                    pathParameters(parameterWithName("postId").description("조회할 게시글의 ID"))
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .when()
            .get("/api/posts/{postId}", savePost1.getId())
        .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("게시글 전체 조회 요청이 정상적인 경우 게시글 전체 조회 성공")
    void findAll_post_success() {
        given(this.spec)
            .filter(
                document("post-findAll"
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .when()
            .get("/api/posts")
        .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
