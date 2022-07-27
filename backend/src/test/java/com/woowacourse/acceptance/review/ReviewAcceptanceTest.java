package com.woowacourse.acceptance.review;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.study.service.request.CreateStudyRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("리뷰 인수 테스트")
public class ReviewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String jwtToken;
    private Long studyId;

    @BeforeEach
    void setUp() {
        jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com")
        );
        final CreateStudyRequest createStudyRequest = CreateStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("짱구의 우당탕탕 자바 스터디입니다.").startDate(LocalDate.now().plusDays(1))
                .build();
        studyId = createStudy(jwtToken, createStudyRequest);
        jdbcTemplate.update("INSERT INTO study_member(study_id, member_id) VALUES (1, 1)");
    }

    @DisplayName("필수 데이터인 후기 내용이 없는 경우 400을 반환한다.")
    @Test
    void get400WhenSetBlankToRequiredField() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("");
//        final String jwtToken = getBearerTokenBySignInOrUp(
//                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

//        createdReview(jwtToken, studyId, writeReviewRequest);
        RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .pathParams("study-id", studyId)
                .body(writeReviewRequest)
                .when().log().all()
                .post("/api/studies/{study-id}/reviews")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("정상적으로 리뷰를 작성한다.")
    @Test
    void get401WhenUsingInvalidToken() {
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("후기 내용입니다.");
//        final String jwtToken = getBearerTokenBySignInOrUp(
//                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));

        final long l = createdReview(jwtToken, studyId, writeReviewRequest);
    }

    public static ValidatableResponse requestWriteReview(final String jwtToken, final String location, final WriteReviewRequest writeReviewRequest) {
        return RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .contentType(ContentType.JSON)
                .pathParams("study-id", location)
                .body(writeReviewRequest)
                .when().log().all()
                .post("/api/studies/{study-id}/reviews")
                .then().log().all();
    }
}
