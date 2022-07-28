package com.woowacourse.acceptance.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("리뷰 인수 테스트")
public class ReviewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    protected void setRestAssuredPort() {
        super.setRestAssuredPort();
        getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "verus", "https://image", "github.com"));
        jdbcTemplate.update(
                "INSERT INTO study(id, title, excerpt, thumbnail, status, description, current_member_count, created_at, owner_id, start_date) VALUES (1, '짱구 스터디', '짱구 설명', 'jjanggu thumbnail', 'OPEN', '짱구입니다.', 1, '2000-01-01T11:58:20.551705', 1, '2000-01-02T11:56:32.123567')");
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
    }

    @DisplayName("정상적으로 리뷰를 작성한다.")
    @Test
    void writeReview() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("후기 내용입니다.");
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        final Long reviewId = createReview(jwtToken, 1L, writeReviewRequest);
        assertThat(reviewId).isNotNull();
    }

    @DisplayName("참여하지 않은 스터디에 리뷰를 작성하려는 경우 401 에러를 반환한다.")
    @Test
    void writeReviewByNonParticipateStudy() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("후기 내용입니다.");
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(2L, "verus", "https://image", "github.com"));

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParams("study-id", 1L)
                .body(writeReviewRequest)
                .when().post("/api/studies/{study-id}/reviews")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
