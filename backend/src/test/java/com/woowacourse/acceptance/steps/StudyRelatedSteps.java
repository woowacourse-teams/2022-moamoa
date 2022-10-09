package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StudyRelatedSteps extends Steps {

    private final Long studyId;
    private final String token;
    private RequestSpecification spec;

    StudyRelatedSteps(final Long studyId, final String token) {
        this.studyId = studyId;
        this.token = token;
        this.spec = RestAssured.given();
    }

    public CommunityArticleRelatedSteps 게시글을() {
        return new CommunityArticleRelatedSteps(token, studyId);
    }

    public LinkArticleRelatedSteps 링크를() {
        return new LinkArticleRelatedSteps(token, studyId);
    }

    public void 참여한다() {
        spec.log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public Long 리뷰를_작성한다(String content) {
        try {
            final String location = spec.log().all()
                    .header(AUTHORIZATION, token)
                    .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(objectMapper.writeValueAsString(new ReviewRequest(content)))
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

    public Long 공지사항을_작성한다(final String title, final String content) {
        try {
            final String location = spec.log().all()
                    .header(AUTHORIZATION, token)
                    .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(objectMapper.writeValueAsString(new ArticleRequest(title, content)))
                    .pathParam("study-id", studyId)
                    .when().log().all()
                    .post("/api/studies/{study-id}/notice/articles")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/notice/articles/", ""));
        } catch (Exception e) {
            Assertions.fail("공지사항 작성 실패");
            return null;
        }
    }
}
