package com.spectrum.controller.post;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.spectrum.common.auth.provider.JwtProvider;
import com.spectrum.controller.post.dto.PostCreateRequest;
import com.spectrum.controller.post.dto.PostUpdateRequest;
import com.spectrum.documentationtest.InitIntegrationDocsTest;
import com.spectrum.domain.post.Post;
import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import com.spectrum.repository.post.PostRepository;
import com.spectrum.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;


public class PostControllerTest extends InitIntegrationDocsTest {

    private final static Long USER_ID = 1L;
    private final static Long POST_ID = 1L;
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    Post savePost1;
    Post savePost2;
    Post savePost3;
    User user;
    String validToken;
    private static final Long LAST_POST_ID = 10L;
    private static final int PAGE_SIZE = 10;

    @BeforeEach
    void init() {
        user = userRepository.save(
            new User(1L, "123456789", "username", "email", "imageUrl", Authority.GUEST)
        );
        savePost1 = postRepository.save(new Post("title1", "content1", USER_ID));
        savePost2 = postRepository.save(new Post("title2", "content2", USER_ID));
        savePost3 = postRepository.save(new Post("title3", "content3", USER_ID));
        validToken = jwtProvider.createAccessToken(String.valueOf(USER_ID));
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
    @DisplayName("게시글 수정 요청이 정상적인 경우라면 게시글 수정 성공")
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
            .put("/api/posts/{postId}", POST_ID)

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
            .delete("/api/posts/{postId}", POST_ID)

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
    @DisplayName("게시글 페이지 조회 요청이 정상적인 경우 게시글 페이지 조회 성공")
    void find_by_page_post_success() {
        given(this.spec)
            .filter(
                document("post-by-page",
                    requestParameters(
                        parameterWithName("cursor").description("마지막 게시글의 번호"),
                        parameterWithName("size").description("페이지 크기")
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .queryParam("cursor", LAST_POST_ID)
            .queryParam("size", PAGE_SIZE)

            .when()
            .get("/api/posts")

            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("게시글 페이지 조회 요청이 잘못된 경우 예외 발생")
    void find_by_page_post_failure() {
        int invalidLastPostId = -1;
        int invalidPageSize = 20;

        given(this.spec)
            .filter(
                document("invalid-post-by-page",
                    requestParameters(
                        parameterWithName("cursor").description("마지막 게시글의 번호"),
                        parameterWithName("size").description("페이지 크기")
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .queryParam("cursor", invalidLastPostId)
            .queryParam("size", invalidPageSize)

            .when()
            .get("/api/posts")

            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("정상적인 게시글 검색 요청이 검색 성공")
    void find_by_search_page_post_success() {
        String searchType = "title";
        String searchValue = "title";

        given(this.spec)
            .filter(
                document("search_post",
                    requestParameters(
                        parameterWithName("type").description("검색타입"),
                        parameterWithName("value").description("검색어"),
                        parameterWithName("cursor").description("마지막 게시글의 번호"),
                        parameterWithName("size").description("페이지 크기")
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .queryParam("type", searchType)
            .queryParam("value", searchValue)
            .queryParam("cursor", LAST_POST_ID)
            .queryParam("size", PAGE_SIZE)

            .when()
            .get("/api/posts/search")

            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("게시글 검색 요청이 잘못된 경우 예외 발생")
    void find_by_search_page_post_failure() {
        String invalidSearchType = "invalidType";
        String invalidSearchValue = "invalidValue";

        given(this.spec)
            .filter(
                document("post-search-invalid",
                    requestParameters(
                        parameterWithName("type").description("검색타입"),
                        parameterWithName("value").description("검색어"),
                        parameterWithName("cursor").description("마지막 게시글의 번호"),
                        parameterWithName("size").description("페이지 크기")
                    )
                )
            )
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", MediaType.APPLICATION_JSON_VALUE)
            .queryParam("type", invalidSearchType)
            .queryParam("value", invalidSearchValue)
            .queryParam("cursor", LAST_POST_ID)
            .queryParam("size", PAGE_SIZE)

            .when()
            .get("/api/posts/search")

            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
