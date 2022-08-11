package com.woowacourse.acceptance.test.tag;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TagAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 태그 목록을 조회한다.")
    @Test
    void getAllFilters() {
        RestAssured.given(spec).log().all()
                .filter(document("tags/list"))
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags", hasSize(5))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("Java", "4기", "BE", "FE", "React"))
                .body("tags.description", contains("자바", "우테코4기", "백엔드", "프론트엔드", "리액트"))
                .body("tags.category.id", contains(3, 1, 2, 2, 3))
                .body("tags.category.name", contains("subject", "generation", "area", "area", "subject"));
    }

    @DisplayName("공백의 이름일 경우 전체 필터 목록을 조회한다.")
    @Test
    void getAllFiltersByBlankFilterName() {
        RestAssured.given().log().all()
                .queryParam("name", "  \t  ")
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags", hasSize(5))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("Java", "4기", "BE", "FE", "React"))
                .body("tags.description", contains("자바", "우테코4기", "백엔드", "프론트엔드", "리액트"))
                .body("tags.category.id", contains(3, 1, 2, 2, 3))
                .body("tags.category.name", contains("subject", "generation", "area", "area", "subject"));
    }

    @DisplayName("이름을 포함한 필터 목록을 대소문자 구분없이 조회한다.")
    @Test
    void getFiltersByFilterName() {
        RestAssured.given().log().all()
                .queryParam("name", "ja")
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags", hasSize(1))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("Java"))
                .body("tags.description", contains("자바"))
                .body("tags.category.id", contains(3))
                .body("tags.category.name", contains("subject"));
    }

    @DisplayName("카테고리와 이름으로 필터 목록을 조회한다.")
    @Test
    void getFiltersByCategory() {
        RestAssured.given(spec).log().all()
                .filter(document("tags/search"))
                .queryParam("name", "a")
                .queryParam("category", 3L)
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags", hasSize(2))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("Java", "React"))
                .body("tags.description", contains("자바", "리액트"))
                .body("tags.category.id", contains(3, 3))
                .body("tags.category.name", contains("subject", "subject"));
    }
}
