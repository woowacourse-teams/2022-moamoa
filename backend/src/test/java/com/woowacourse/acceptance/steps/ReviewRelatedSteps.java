package com.woowacourse.acceptance.steps;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.woowacourse.acceptance.document.ReviewDocument;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import com.woowacourse.moamoa.studyroom.service.response.ReviewsResponse;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReviewRelatedSteps extends Steps<ReviewRelatedSteps, ReviewDocument> {

    private final String token;
    private final Long studyId;

    public ReviewRelatedSteps(final String token, final Long studyId) {
        this.token = token;
        this.studyId = studyId;
    }

    public Long 작성한다(String content) {
        try {
            final String location = spec.log().all()
                    .header(AUTHORIZATION, token)
                    .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .pathParams("study-id", studyId)
                    .body(new ReviewRequest(content))
                    .when().post("/api/studies/{study-id}/reviews")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().header(HttpHeaders.LOCATION);
            return Long.parseLong(location.replaceAll("/api/studies/" + studyId + "/reviews/", ""));
        } catch (NumberFormatException e) {
            Assertions.fail("리뷰 ID 추출 실패");
            return null;
        }
    }

    public ReviewsResponse 목록_조회한다(final int size) {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}/reviews?size=" + size)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);
    }

    public ReviewsResponse 목록_조회한다() {
        return spec.log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}/reviews")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);
    }

    public void 삭제한다(final long reviewId) {
        spec.log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("review-id", reviewId)
                .when().log().all()
                .delete("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    public void 수정한다(final long reviewId, final ReviewRequest request) {
        spec.log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .pathParam("review-id", reviewId)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
