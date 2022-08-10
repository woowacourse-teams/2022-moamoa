package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class StudyRelatedSteps extends Steps {

    private final Long studyId;
    private final String token;

    StudyRelatedSteps(final Long studyId, final String token) {
        this.studyId = studyId;
        this.token = token;
    }

    public void 참여한다() {
        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .when().log().all()
                .post("/api/studies/" + studyId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    public long 리뷰를_작성한다(String content) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(objectMapper.writeValueAsString(new WriteReviewRequest(content)))
                    .when().post("/api/studies/{study-id}/reviews")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/reviews/", ""));
        } catch (Exception e) {
            Assertions.fail("리뷰 작성 실패");
            return -1;
        }
    }

    public long 게시글을_작성한다(final String title, final String content) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(org.apache.http.HttpHeaders.AUTHORIZATION, token)
                    .header(org.apache.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
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
            return -1;
        }
    }
}
