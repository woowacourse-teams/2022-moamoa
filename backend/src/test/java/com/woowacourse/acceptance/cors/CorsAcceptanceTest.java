package com.woowacourse.acceptance.cors;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CorsAcceptanceTest extends AcceptanceTest {

    @DisplayName("cors 적용 여부 확인")
    @Test
    void corsTest() {
        RestAssured.given()
                .header("Origin", "https://xxx.com")
                .header("Access-Control-Request-Method", "GET")
                .when()
                .options("/api/studies")
                .then()
                .statusCode(200);
    }
}
