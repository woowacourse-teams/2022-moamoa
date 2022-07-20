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
                .body("excerpt", is("자바 설명"))
                .body("thumbnail", is("java thumbnail"))
                .body("status", is("OPEN"))
                .body("description", is("그린론의 우당탕탕 자바 스터디입니다."))
                .body("currentMemberCount", is(1))
                .body("maxMemberCount", is(10))
                .body("createdAt", is("2021-11-08"))
                .body("enrollmentEndDate", is(""))
                .body("startDate", is(""))
                .body("endDate", is(""))
                .body("owner.id", is(2))
                .body("owner.username", is("greenlawn"))
                .body("owner.imageUrl", is("https://image"))
                .body("owner.profileUrl", is("github.com"))
                .body("members.id", not(empty()))
                .body("members.username", contains("dwoo", "verus"))
                .body("members.imageUrl", contains("https://image", "https://image"))
                .body("members.profileUrl", contains("github.com", "github.com"))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("Java", "4기", "BE"));
    }
}
