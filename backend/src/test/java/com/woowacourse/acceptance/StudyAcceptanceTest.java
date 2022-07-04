package com.woowacourse.acceptance;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.woowacourse.moamoa.MoamoaApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {MoamoaApplication.class}
)
@Sql("/init.sql")
public class StudyAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Disabled
    @DisplayName("첫번째 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getFirstPageOfStudies() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies?page=1&size=3")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(true))
                .body("studies", hasSize(3))
                .body("studies.id", contains(notNullValue(), notNullValue(), notNullValue()))
                .body("studies.title", contains("Java 스터디", "React 스터디", "javaScript 스터디"))
                .body("studies.description", contains("자바 설명", "리액트 설명", "자바스크립트 설명"))
                .body("studies.thumbnail", contains("java thumbnail", "react thumbnail", "javascript thumbnail"))
                .body("studies.status", contains("OPEN", "OPEN", "OPEN"));
    }

    @Disabled
    @DisplayName("마지막 페이지의 스터디 목록을 조회 한다.")
    @Test
    public void getLastPageOfStudies() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies?page=2&size=3")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("hasNext", is(false))
                .body("studies", hasSize(2))
                .body("studies.id", contains(notNullValue(), notNullValue()))
                .body("studies.title", contains("HTTP 스터디", "알고리즘 스터디"))
                .body("studies.description", contains("HTTP 설명", "알고리즘 설명"))
                .body("studies.thumbnail", contains("http thumbnail", "algorithm thumbnail"))
                .body("studies.status", contains("CLOSE", "CLOSE"));
    }

    @DisplayName("잘못된 페이징 정보로 목록을 조회시 400에러를 응답한다.")
    @ParameterizedTest
    @CsvSource({"0,3", "1,0", "one,1", "1,one"})
    public void response400WhenRequestByInvalidPagingInfo(String page, String size) {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies?page=" + page + "&size=" + size)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", not(blankOrNullString()));
    }

    @Disabled
    @DisplayName("페이징 정보가 없는 경우에는 기본값을 사용해 스터디 목록을 조회한다.")
    @Test
    public void getStudiesByDefaultPagingInfo() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("studies.id", contains(
                        notNullValue(), notNullValue(), notNullValue(), notNullValue(), notNullValue()))
                .body("studies.title", contains(
                        "Java 스터디", "React 스터디", "javaScript 스터디", "HTTP 스터디", "알고리즘 스터디"))
                .body("studies.description", contains(
                        "자바 설명", "리액트 설명", "자바스크립트 설명", "HTTP 설명", "알고리즘 설명"))
                .body("studies.thumbnail", contains(
                        "java thumbnail", "react thumbnail", "javascript thumbnail", "http thumbnail",
                        "algorithm thumbnail"))
                .body("studies.status", contains("OPEN", "OPEN", "OPEN", "CLOSE", "CLOSE"));
    }
}
