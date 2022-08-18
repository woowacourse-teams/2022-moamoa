package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class StudyParticipantAcceptanceTest extends AcceptanceTest {

    @DisplayName("아직 스터디에 가입되지 않은 회원은 스터디에 참여가 가능하다.")
    @Test
    void participateStudy() {
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).모집인원은(10).생성한다();
        String token = 디우가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/participant"))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .cookie(ACCESS_TOKEN, token)
                .pathParam("study-id", 자바_스터디_ID)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("스터디를 탈퇴한다.")
    @Test
    void leaveStudy() {
        final LocalDate 지금 = LocalDate.now();
        final Long studyId = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        디우가().로그인하고().스터디에(studyId).참여한다();

        final String token = 디우가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/leave"))
                .cookie(ACCESS_TOKEN, token)
                .pathParam("study-id", studyId)
                .when().log().all()
                .delete("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final StudyDetailResponse studyDetailResponse = RestAssured.given().log().all()
                .pathParam("study-id", studyId)
                .when().log().all()
                .get("/api/studies/{study-id}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(StudyDetailResponse.class);

        assertAll(
                () -> assertThat(studyDetailResponse.getCurrentMemberCount()).isEqualTo(1),
                () -> assertThat(studyDetailResponse.getMembers()).isEmpty()
        );
    }
}
