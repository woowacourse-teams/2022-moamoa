package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.common.advice.response.ErrorResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StudyParticipantAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디를 탈퇴한다.")
    @Test
    void leaveStudy() {
        final LocalDate 지금 = LocalDate.now();
        final long studyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        디우가().로그인하고().스터디에(studyId).참여한다();

        final String token = 디우가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/leave",
                        requestHeaders(
                                headerWithName("Authorization").description("JWT Token")
                        )))
                .header(HttpHeaders.AUTHORIZATION, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .delete("/api/studies/{study-id}/participants")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final ErrorResponse response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", studyId)
                .body("리뷰 작성 내용")
                .when().log().all()
                .post("/api/studies/{study-id}/reviews")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        assertThat(response.getMessage()).isEqualTo("스터디에 후기를 작성할 수 없습니다.");
    }
}
