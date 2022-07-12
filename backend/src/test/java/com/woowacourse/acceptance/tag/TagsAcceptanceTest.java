package com.woowacourse.acceptance.tag;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TagsAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 태그 목록을 조회한다.")
    @Test
    void getAllTags() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags.id", contains(notNullValue(), notNullValue(), notNullValue()))
                .body("tags.tagName", contains("Java", "BE", "4기"));
    }

    @DisplayName("공백의 태그 이름일 경우 전체 태그 목록을 조회한다.")
    @Test
    void getAllTagsByBlankTagName() {
        RestAssured.given().log().all()
                .queryParam("tagname", "  \t  ")
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags.id", contains(notNullValue(), notNullValue(), notNullValue()))
                .body("tags.tagName", contains("Java", "BE", "4기"));
    }

    @DisplayName("태그 이름을 포함한 태그 목록을 대소문자 구분없이 조회한다.")
    @Test
    void getTagsByTagName() {
        RestAssured.given().log().all()
                .queryParam("tagname", "ja")
                .when().log().all()
                .get("/api/tags")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("tags.id", contains(notNullValue()))
                .body("tags.tagName", contains("Java"));
    }
}
