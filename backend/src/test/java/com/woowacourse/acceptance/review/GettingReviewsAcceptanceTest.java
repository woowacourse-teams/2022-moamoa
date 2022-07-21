package com.woowacourse.acceptance.review;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.woowacourse.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class GettingReviewsAcceptanceTest extends AcceptanceTest {

    @DisplayName("스터디에 달린 리뷰 목록을 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/1/reviews")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("reviews.id", not(empty()))
                .body("reviews.member.id", not(empty()))
                .body("reviews.member.username", contains("jjanggu", "greenlawn"))
                .body("reviews.member.imageUrl", contains("https://image", "https://image"))
                .body("reviews.member.profileUrl", contains("github.com", "github.com"))
                .body("reviews.createdDate", contains("2021-11-08", "2021-11-08"))
                .body("reviews.lastModifiedDate", contains("2021-11-08", "2021-11-08"))
                .body("totalResults", is(2));
    }
}
