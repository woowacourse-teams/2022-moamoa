package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.document.CommunityArticleDocument;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CommunityArticleRelatedSteps extends Steps<CommunityArticleRelatedSteps, CommunityArticleDocument> {

    private final String token;
    private final Long studyId;

    CommunityArticleRelatedSteps(final String token, final Long studyId) {
        this.token = token;
        this.studyId = studyId;
        this.spec = RestAssured.given();
    }

    public Long 작성한다(final String title, final String content) {
        try {
            final String location = spec.log().all()
                    .header(AUTHORIZATION, token)
                    .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(objectMapper.writeValueAsString(new ArticleRequest(title, content)))
                    .pathParam("study-id", studyId)
                    .when().log().all()
                    .post("/api/studies/{study-id}/community/articles")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/community/articles/", ""));
        } catch (Exception e) {
            Assertions.fail("게시글 작성 실패");
            return null;
        }
    }

    public ArticleResponse 조회한다(final Long articleId) {
        return spec.log().all()
                .header(org.apache.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleResponse.class);
    }

    public void 찾을_수_없다(final long articleId) {
        spec.log().all()
                .header(org.apache.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles/{article-id}")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public void 삭제한다(final long articleId) {
        spec.log().all()
                .header(org.apache.http.HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .pathParam("article-id", articleId)
                .filter(document("article/delete",
                        requestHeaders(
                                headerWithName(org.apache.http.HttpHeaders.AUTHORIZATION).description("JWT 토큰")
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
    }

    public ArticleSummariesResponse 목록_조회한다() {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleSummariesResponse.class);
    }

    public ArticleSummariesResponse 목록_조회한다(final int page, final int size) {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .queryParam("page", page)
                .queryParam("size", size)
                .when().log().all()
                .get("/api/studies/{study-id}/community/articles")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ArticleSummariesResponse.class);
    }

    public void 수정한다(final Long articleId, final String title, final String content) {
        ArticleRequest request = new ArticleRequest(title, content);

        try {
            spec.log().all()
                    .header(org.apache.http.HttpHeaders.AUTHORIZATION, token)
                    .pathParam("study-id", studyId)
                    .pathParam("article-id", articleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(objectMapper.writeValueAsString(request))
                    .when().log().all()
                    .put("/api/studies/{study-id}/community/articles/{article-id}")
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        } catch (Exception e) {
            Assertions.fail("게시글 수정 실패");
        }
    }
}
