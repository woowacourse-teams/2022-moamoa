package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.slack.api.model.Attachment;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StudyRelatedSteps extends Steps {

    private final Long studyId;
    private final String token;

    StudyRelatedSteps(final Long studyId, final String token) {
        this.studyId = studyId;
        this.token = token;
    }

    public HttpStatus 참여를_시도한다() {
        slackAlarmMockServer.sendAlarm();

        final int code = RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .extract().statusCode();
        return HttpStatus.valueOf(code);
    }

    public void 참여에_성공한다() {
        slackAlarmMockServer.sendAlarm();

        RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public void 참여에_성공하고_방장에게_알림을_보낸다(final String ownerChannel) {
        final SlackMessageRequest slackMessageRequest = new SlackMessageRequest(ownerChannel,
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));
        slackAlarmMockServer.sendAlarmWithExpect(slackMessageRequest);

        RestAssured.given().log().all()
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
            final String location = RestAssured.given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
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
    public Long 링크를_공유한다(final LinkArticleRequest request) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(AUTHORIZATION, token)
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

    public Long 공지사항을_작성한다(final String title, final String content) {
        try {
            final String location = RestAssured.given().log().all()
                    .header(org.apache.http.HttpHeaders.AUTHORIZATION, token)
                    .header(org.apache.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
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

    public Long 게시글을_작성한다(final String title, final String content) {
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
            return null;
        }
    }

    public HttpStatus 탈퇴를_시도한다() {
        final int code = RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .delete("/api/studies/{study-id}/members")
                .then().log().all()
                .extract().statusCode();
        return HttpStatus.valueOf(code);
    }
}
