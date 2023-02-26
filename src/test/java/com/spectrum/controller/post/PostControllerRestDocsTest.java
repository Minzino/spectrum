package com.spectrum.controller.post;

import com.spectrum.controller.post.dto.PostRegisterRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@DisplayName("문서화 : Post 등록")
public class PostControllerRestDocsTest extends InitIntegrationDocsTest {

    @Test
    @DisplayName("게시글 생성 요청이 정상적인 경우라면 게시글 생성 후 상태코드를 반환한다.")
    void register_post_success() {
        PostRegisterRequest request = new PostRegisterRequest("제목1", "내용1");

        given(this.spec)
                .filter(
                        document("post-create",
                                requestFields(
                                        fieldWithPath("title").description("게시글 제목").type(JsonFieldType.STRING),
                                        fieldWithPath("content").description("게시글 내용").type(JsonFieldType.STRING)
                                ),
                                responseFields(
                                        fieldWithPath("postId").description("게시글 ID").type(JsonFieldType.STRING),
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
}
