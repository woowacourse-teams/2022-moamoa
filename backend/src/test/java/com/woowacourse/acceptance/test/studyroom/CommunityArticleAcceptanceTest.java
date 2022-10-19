package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.slack.api.model.Attachment;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class CommunityArticleAcceptanceTest extends AcceptanceTest {

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
                .filter(document("article/create",
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
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", articleId)
                .filter(document("article/get",
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

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();

        final ArticleResponse expectedResponse = ArticleResponse.builder()
                .id(articleId)
                .author(new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl()))
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
                .filter(document("article/delete",
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
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", 게시글_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("스터디 커뮤니티 전체 게시글을 조회한다.")
    @Test
    void getStudyCommunityArticles() {
        // arrange
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        그린론이().로그인하고().스터디에(자바_스터디_ID).게시글을_작성한다("자바 게시글 제목1", "자바 게시글 내용1");
        베루스가().로그인한다();

        final SlackMessageRequest slackMessageRequest = new SlackMessageRequest("green",
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));

        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다(slackAlarmMockServer, slackMessageRequest);

        long 자바_게시글2_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).게시글을_작성한다("자바 게시글 제목2", "자바 게시글 내용2");
        long 자바_게시글3_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).게시글을_작성한다("자바 게시글 제목3", "자바 게시글 내용3");
        long 자바_게시글4_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).게시글을_작성한다("자바 게시글 제목4", "자바 게시글 내용4");

        long 리액트_스터디_ID = 베루스가().로그인하고().리액트_스터디를().시작일자는(LocalDate.now()).생성한다();
        베루스가().로그인하고().스터디에(리액트_스터디_ID).게시글을_작성한다("리액트 게시글 제목", "리액트 게시글 내용");

        // act
        final ArticleSummariesResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 자바_스터디_ID)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .filter(document("article/list",
                        pathParameters(
                                parameterWithName("study-id").description("스터디 ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈")
                        ),
                        responseFields(
                                fieldWithPath("articles").type(JsonFieldType.ARRAY).description("게시물 목록"),
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별 ID"),
                                fieldWithPath("articles[].author").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("articles[].author.id").type(JsonFieldType.NUMBER)
                                        .description("작성자 github ID"),
                                fieldWithPath("articles[].author.username").type(JsonFieldType.STRING)
                                        .description("작성자 github 사용자 이름"),
                                fieldWithPath("articles[].author.imageUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 이미지 URL"),
                                fieldWithPath("articles[].author.profileUrl").type(JsonFieldType.STRING)
                                        .description("작성자 github 프로필 URL"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].createdDate").type(JsonFieldType.STRING)
                                        .description("게시글 작성일"),
                                fieldWithPath("articles[].lastModifiedDate").type(JsonFieldType.STRING)
                                        .description("게시글 수정일"),
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("lastPage").type(JsonFieldType.NUMBER).description("마지막 페이지 번호"),
                                fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("게시글 전체 갯수")
                        )
                ))
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleSummariesResponse.class);

        // assert
        final MemberResponse 베루스_정보 = 베루스가().로그인하고().정보를_가져온다();
        AuthorResponse 베루스 = new AuthorResponse(베루스_정보.getId(), 베루스_정보.getUsername(), 베루스_정보.getImageUrl(), 베루스_정보.getProfileUrl());

        List<ArticleSummaryResponse> articles = List.of(
                new ArticleSummaryResponse(자바_게시글4_ID, 베루스, "자바 게시글 제목4", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(자바_게시글3_ID, 베루스, "자바 게시글 제목3", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(자바_게시글2_ID, 베루스, "자바 게시글 제목2", LocalDate.now(), LocalDate.now())
        );

        assertThat(response).isEqualTo(new ArticleSummariesResponse(articles, 0, 1, 4));
    }

    @DisplayName("스터디 커뮤니티 전체 게시글을 기본 페이징 정보로 조회한다.")
    @Test
    void getStudyCommunityArticlesByDefaultPageable() {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 게시글1_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("자바 게시글 제목1", "자바 게시글 내용1");
        long 게시글2_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("자바 게시글 제목2", "자바 게시글 내용2");
        long 게시글3_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("자바 게시글 제목3", "자바 게시글 내용3");
        long 게시글4_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("자바 게시글 제목4", "자바 게시글 내용4");

        String 토큰 = 그린론이().로그인한다();

        // act
        final ArticleSummariesResponse response = RestAssured.given(spec).log().all()
                .pathParam("study-id", 스터디_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleSummariesResponse.class);

        // assert
        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        AuthorResponse 그린론 = new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl());

        List<ArticleSummaryResponse> articles = List.of(
                new ArticleSummaryResponse(게시글4_ID, 그린론, "자바 게시글 제목4", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(게시글3_ID, 그린론, "자바 게시글 제목3", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(게시글2_ID, 그린론, "자바 게시글 제목2", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(게시글1_ID, 그린론, "자바 게시글 제목1", LocalDate.now(), LocalDate.now())
        );

        assertThat(response).isEqualTo(new ArticleSummariesResponse(articles, 0, 0, 4));
    }

    @DisplayName("커뮤니티 글을 수정한다.")
    @Test
    void updateArticleToCommunity() throws JsonProcessingException {
        // arrange
        long 스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(LocalDate.now()).생성한다();
        long 게시글_ID = 그린론이().로그인하고().스터디에(스터디_ID).게시글을_작성한다("게시글 제목", "게시글 내용");
        String 토큰 = 그린론이().로그인한다();

        final ArticleRequest request = new ArticleRequest("게시글 제목 수정", "게시글 내용 수정");

        // act
        RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, 토큰)
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", 게시글_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(request))
                .filter(document("article/update",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별 번호"),
                                parameterWithName("article-id").description("게시글 식별 번호")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 수정 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용 수정")
                        )
                ))
                .when().log().all()
                .put("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // assert
        final ArticleResponse response = RestAssured
                .given().log().all()
                .pathParam("study-id", 스터디_ID)
                .pathParam("article-id", 게시글_ID)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ArticleResponse.class);

        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final AuthorResponse authorResponse = new AuthorResponse(그린론_정보.getId(), 그린론_정보.getUsername(), 그린론_정보.getImageUrl(), 그린론_정보.getProfileUrl());

        assertThat(response).isEqualTo(new ArticleResponse(게시글_ID, authorResponse, "게시글 제목 수정",
                "게시글 내용 수정", LocalDate.now(), LocalDate.now()));
    }
}
