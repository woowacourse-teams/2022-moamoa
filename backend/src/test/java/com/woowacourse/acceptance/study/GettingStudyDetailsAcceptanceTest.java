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
                .get("/api/studies/2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("title", is("React 스터디"))
                .body("excerpt", is("리액트 설명"))
                .body("thumbnail", is("react thumbnail"))
                .body("status", is("OPEN"))
                .body("description", is("디우의 뤼액트 스터디입니다."))
                .body("currentMemberCount", is(4))
                .body("maxMemberCount", is(5))
                .body("createdAt", is("2021-11-08"))
                .body("enrollmentEndDate", is("2021-11-09"))
                .body("startDate", is("2021-11-10"))
                .body("endDate", is("2021-12-08"))
                .body("owner.id", is(3))
                .body("owner.username", is("dwoo"))
                .body("owner.imageUrl", is("https://image"))
                .body("owner.profileUrl", is("github.com"))
                .body("members.id", not(empty()))
                .body("members.username", contains("jjanggu", "greenlawn", "verus"))
                .body("members.imageUrl", contains("https://image", "https://image", "https://image"))
                .body("members.profileUrl", contains("github.com", "github.com", "github.com"))
                .body("tags.id", not(empty()))
                .body("tags.name", contains("4기", "FE", "React"));
    }
}
