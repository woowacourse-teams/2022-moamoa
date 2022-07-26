package com.woowacourse.acceptance.review;

import static com.woowacourse.acceptance.study.CreatingStudyAcceptanceTest.requestCreateStudy;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("리뷰 인수 테스트")
public class ReviewAcceptanceTest extends AcceptanceTest {

    @DisplayName("필수 데이터인 후기 내용이 없는 경우 400을 반환한다.")
    @Test
    void get400WhenSetBlankToRequiredField() {
        final String jwtToken = getBearerTokenBySignInOrUp(
                new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        final Map<String, String> studyRequest = Map.of("title", "제목", "excerpt", "자바를 공부하는 스터디", "thumbnail", "image",
                "description", "스터디 상세 설명입니다.", "startDate", LocalDate.now().plusDays(5).format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")), "endDate", "");

        final String location = requestCreateStudy(jwtToken, studyRequest);
        final Map<String, String> reviewRequest = Map.of("content", "");

        requestWriteReview(jwtToken, location, reviewRequest);
    }

    public static void requestWriteReview(final String jwtToken, final String location, final Map<String, String> reviewRequest) {
        RestAssured.given().log().all()
                .auth().oauth2(jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reviewRequest)
                .when().log().all()
                .post("/api/studies/" + location + "/reviews")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
