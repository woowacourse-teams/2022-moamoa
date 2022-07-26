package com.woowacourse.acceptance.study;

import static org.hamcrest.Matchers.is;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.controller.AuthenticationArgumentResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;

public class GettingMyStudiesAcceptanceTest extends AcceptanceTest {

    @MockBean
    private AuthenticationArgumentResolver authenticationArgumentResolver;

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/my/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("studies[0].id", is(1))
                .body("studies[0].title", is("Java 스터디"))
                .body("studies[0].studyStatus", is("IN_PROGRESS"))
                .body("studies[0].currentMemberCount", is(3))
                .body("studies[0].maxMemberCount", is(10))
                .body("studies[0].startDate", is("2022-07-26"))
                .body("studies[0].endDate", is("2022-08-26"))
                .body("studies[0].owner.username", is("jaejae-yoo"))
                .body("studies[0].owner.imageUrl", is("images/123"))
                .body("studies[0].owner.profileUrl", is("https://github.com/user/jaejae-yoo"))
                .body("studies[0].tags[0].id", is(1))
                .body("studies[0].tags[0].name", is("BE"))
                .body("studies[0].tags[1].id", is(5))
                .body("studies[0].tags[1].name", is("Java"));
    }
}
