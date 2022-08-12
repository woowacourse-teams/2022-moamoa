package com.woowacourse.acceptance.test.notice;

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

public class NoticeAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디에 공지사항을 작성한다.")
    @Test
    void writeNotice() throws Exception {
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
                .filter(document("write/notice",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("공지사항 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("공지사항 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 공지사항 url"),
                                headerWithName("Access-Control-Allow-Headers").description("접근 가능한 헤더")
                        ))
                )
                .post("/api/studies/{study-id}/notice/articles")
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
                .filter(document("get/notice",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 ID"),
                                parameterWithName("article-id").description("공지사항 식별 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("공지사항 식별 ID"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("작성자 github ID"),
                                fieldWithPath("author.username").type(JsonFieldType.STRING)
                                        .description("작성자 github 사용자 이름"),
                                fieldWithPath("author.imageUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 이미지 URL"),
                                fieldWithPath("author.profileUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 프로필 URL"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("공지사항 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("공지사항 내용"),
                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("공지사항 작성일"),
                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("공지사항 수정일")
                        )
                ))
                .when().log().all()
                .get("/api/studies/{study-id}/notice/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleResponse.class);

        final ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .author(new AuthorResponse(그린론_깃허브_ID, 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL))
                .title("공지사항 제목")
                .content("공지사항 내용")
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
