package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StudyRelatedSteps extends Steps {

    private static final String ACCESS_TOKEN = "accessToken";

    private final Long studyId;
    private final String token;

    StudyRelatedSteps(final Long studyId, final String token) {
        this.studyId = studyId;
        this.token = token;
    }

    public void 참여한다() {
        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .cookie(ACCESS_TOKEN, token)
                .when().log().all()
                .post("/api/studies/" + studyId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    public Long 리뷰를_작성한다(String content) {
        try {
            final String location = RestAssured.given().log().all()
                    .cookie(ACCESS_TOKEN, token)
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
            return null;
        }
    }

    public Long 링크를_공유한다(final CreatingLinkRequest request) {
        try {
            final String location = RestAssured.given().log().all()
                    .cookie(ACCESS_TOKEN, token)
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(objectMapper.writeValueAsString(request))
                    .when().post("/api/studies/{study-id}/reference-room/links")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/reference-room/links/", ""));
        } catch (Exception e) {
            Assertions.fail("링크 공유 작성 실패");
            return null;
        }
    }

    public Long 게시글을_작성한다(final String title, final String content) {
        try {
            final String location = RestAssured.given().log().all()
                    .cookie(ACCESS_TOKEN, token)
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
            return null;
        }
    }
}
