package com.woowacourse.acceptance.test.study;

import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ParticipationStudyAcceptanceTest extends AcceptanceTest {

    @DisplayName("아직 스터디에 가입되지 않은 회원은 스터디에 참여가 가능하다.")
    @Test
    void participateStudy() {
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 그린론이().로그인하고().자바_스터디를().시작일자는(지금).모집인원은(10).생성한다();
        String token = 디우가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("studies/participant"))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .pathParam("study-id", 자바_스터디_ID)
                .when().log().all()
                .post("/api/studies/{study-id}/members")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
