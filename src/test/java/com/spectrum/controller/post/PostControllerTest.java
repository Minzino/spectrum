package com.spectrum.controller.post;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;


public class PostControllerTest extends InitIntegrationDocsTest {

    @Test
    @DisplayName("게시글 생성 요청이 정상적인 경우라면 게시글 생성 성공")
    void create_post_success() {
        PostCreateRequest request = new PostCreateRequest("title", "content",1L);

        given(this.spec)
            .filter(
                document("post-create",
                    requestFields(
                        fieldWithPath("title").description("게시글 제목").type(JsonFieldType.STRING),
                        fieldWithPath("content").description("게시글 내용").type(JsonFieldType.STRING),
                        fieldWithPath("memberId").description("작성자 ID").type(JsonFieldType.NUMBER)
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
