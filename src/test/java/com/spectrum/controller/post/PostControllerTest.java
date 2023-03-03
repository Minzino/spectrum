package com.spectrum.controller.post;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostUpdateRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import com.spectrum.domain.post.Post;
import com.spectrum.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;


public class PostControllerTest extends InitIntegrationDocsTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void init() {
        postRepository.save(new Post("title","content",1L));
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
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
        .body(request)

    .when()
        .post("/api/posts")

    .then()
        .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("게시글 수정 요청이 정상적인 경우라면 게시글 변경 성공")
        void update_post_success() {
        PostUpdateRequest request = new PostUpdateRequest("title", "content");

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
            .body(request)
        .when()
            .put("/api/posts/{postId}", 1L)
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
