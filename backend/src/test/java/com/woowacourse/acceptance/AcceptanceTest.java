package com.woowacourse.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.woowacourse.moamoa.MoamoaApplication;

import io.restassured.RestAssured;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
@Sql("/init.sql")
public class AcceptanceTest {
    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

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
