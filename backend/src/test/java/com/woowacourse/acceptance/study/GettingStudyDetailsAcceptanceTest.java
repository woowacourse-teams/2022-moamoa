package com.woowacourse.acceptance.study;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class GettingStudyDetailsAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디 요약 정보 외에 상세 정보를 포함하여 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("title", is("Java 스터디"))
                .body("excerpt", is("자바를 공부하는 스터디입니다"))
                .body("thumbnail", is("image url"))
                .body("status", is("open"))
                .body("description", is("스터디 상세 설명입니다"))
                .body("current_member_count", is(3))
                .body("max_member_count", is(10))
                .body("deadline", is("2022-07-12"))
                .body("start_date", is("2022-07-12"))
                .body("end_date", is("2022-07-12"))
                .body("owner", is("jaejae-yoo"))
                .body("members.id", not(empty()))
                .body("members.username", contains("tco0427", "jaejae-yoo"))
                .body("members.profileImage", contains("images/132", "images/133"))
                .body("members.profileUrl", contains("https://github.com/user/tco0427", "https://github.com/user/jaejae-yoo"))
                .body("tags.id", not(empty()))
                .body("tags.tagName", contains("spring", "java"));
    }
}
