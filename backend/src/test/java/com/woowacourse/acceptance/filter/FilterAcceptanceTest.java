package com.woowacourse.acceptance.filter;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class FilterAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 필터 목록을 조회한다.")
    @Test
    void getAllFilters() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/filters")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("filters", hasSize(5))
                .body("filters.id", not(empty()))
                .body("filters.name", contains("Java", "4기", "BE", "FE", "React"))
                .body("filters.category.id", contains(3, 1, 2, 2, 3))
                .body("filters.category.name", contains("TAG", "GENERATION", "AREA", "AREA", "TAG"));
    }

    @DisplayName("공백의 이름일 경우 전체 필터 목록을 조회한다.")
    @Test
    void getAllFiltersByBlankFilterName() {
        RestAssured.given().log().all()
                .queryParam("name", "  \t  ")
                .when().log().all()
                .get("/api/filters")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("filters", hasSize(5))
                .body("filters.id", not(empty()))
                .body("filters.name", contains("Java", "4기", "BE", "FE", "React"))
                .body("filters.category.id", contains(3, 1, 2, 2, 3))
                .body("filters.category.name", contains("TAG", "GENERATION", "AREA", "AREA", "TAG"));
    }

    @DisplayName("이름을 포함한 필터 목록을 대소문자 구분없이 조회한다.")
    @Test
    void getFiltersByFilterName() {
        RestAssured.given().log().all()
                .queryParam("name", "ja")
                .when().log().all()
                .get("/api/filters")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("filters", hasSize(1))
                .body("filters.id", not(empty()))
                .body("filters.name", contains("Java"))
                .body("filters.category.id", contains(3))
                .body("filters.category.name", contains("TAG"));
    }

    @DisplayName("카테고리와 이름으로 필터 목록을 조회한다.")
    @Test
    void getFiltersByCategory() {
        RestAssured.given().log().all()
                .queryParam("name", "a")
                .queryParam("category", 3L)
                .when().log().all()
                .get("/api/filters")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("filters", hasSize(2))
                .body("filters.id", not(empty()))
                .body("filters.name", contains("Java", "React"))
                .body("filters.category.id", contains(3, 3))
                .body("filters.category.name", contains("TAG", "TAG"));
    }
}
