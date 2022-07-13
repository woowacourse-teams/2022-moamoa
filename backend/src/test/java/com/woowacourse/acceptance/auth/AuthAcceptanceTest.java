package com.woowacourse.acceptance.auth;

import static org.hamcrest.Matchers.is;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Authorization code를 받아서 token을 발급한다.")
    @Test
    void getToken() {
        RestAssured.given().log().all()
                .param("code")
                .when()
                .post("/api/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("token", is("dummy-token"));
    }
}
