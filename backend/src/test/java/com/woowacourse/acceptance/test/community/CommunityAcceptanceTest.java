package com.woowacourse.acceptance.test.community;

import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_프로필_URL;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleResponse;
import com.woowacourse.moamoa.community.service.response.AuthorResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class CommunityAcceptanceTest extends AcceptanceTest {

    @DisplayName("커뮤니티에 글을 작성한다.")
    @Test
    void writeArticleToCommunity() throws Exception {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        String 토큰 = 그린론이().로그인한다();
        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");

        // act
        final String location = RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, 토큰)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(request))
                .pathParam("study-id", 스터디_ID)
                .when().log().all()
                .filter(document("write/article",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 게시글 url"),
                                headerWithName("Access-Control-Allow-Headers").description("접근 가능한 헤더")
                        ))
                )
                .post("/api/studies/{study-id}/community/articles")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .header(HttpHeaders.LOCATION);

        // assert
        Long articleId = Long.valueOf(location.split("/")[6]);

        final ArticleResponse actualResponse = RestAssured
                .given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, 토큰)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", articleId)
                .filter(document("get/article",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("게시글 식별 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 식별 ID"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("작성자 github ID"),
                                fieldWithPath("author.username").type(JsonFieldType.STRING)
                                        .description("작성자 github 사용자 이름"),
                                fieldWithPath("author.imageUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 이미지 URL"),
                                fieldWithPath("author.profileUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 프로필 URL"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("게시글 작성일"),
                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("게시글 수정일")
                        )
                ))
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleResponse.class);

        final ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .author(new AuthorResponse(그린론_깃허브_ID, 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL))
                .title("게시글 제목")
                .content("게시글 내용")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @DisplayName("스터디 커뮤니티 게시글을 삭제한다.")
    @Test
    void deleteCommunityArticle() {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("게시글 제목", "게시글 내용");
        String 토큰 = 그린론이().로그인한다();

        // act
        RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, 토큰)
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", 게시글_ID)
                .filter(document("delete/article",
                    requestHeaders(
                            headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                    ),
                    pathParameters(
                            parameterWithName("study-id").description("스터디 식별 번호"),
                            parameterWithName("article-id").description("게시글 식별 번호")
                    )
                ))
                .when().log().all()
                .delete("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // assert
        RestAssured
                .given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, 토큰)
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", 게시글_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
