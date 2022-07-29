package com.woowacourse.acceptance.review;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class GettingReviewsAcceptanceTest extends AcceptanceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDataBase() {
        final String jjangguToken = getBearerTokenBySignInOrUp(new GithubProfileResponse(1L, "jjanggu", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(2L, "greenlawn", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(3L, "dwoo", "https://image", "github.com"));
        getBearerTokenBySignInOrUp(new GithubProfileResponse(4L, "verus", "https://image", "github.com"));

        final CreatingStudyRequest request = CreatingStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("짱구의 우당탕탕 자바 스터디입니다.").startDate(LocalDate.now().plusDays(1))
                .build();
        createStudy(jjangguToken, request);

        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용1', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용2', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용3', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 4, '리뷰 내용4', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 1, '리뷰 내용5', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 2, '리뷰 내용6', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
        jdbcTemplate.update("INSERT INTO review(study_id, member_id, content, created_date, last_modified_date) VALUES (1, 3, '리뷰 내용7', '2021-11-08T11:58:20.551705', '2021-11-08T11:58:20.551705')");
    }

    @DisplayName("스터디에 달린 리뷰 목록을 조회할 수 있다.")
    @Test
    public void getReviews() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/1/reviews?size=6")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("reviews.id", not(empty()))
                .body("reviews.member.id", not(empty()))
                .body("reviews.member.username", contains("jjanggu", "greenlawn", "dwoo", "verus", "jjanggu", "greenlawn"))
                .body("reviews.member.imageUrl", contains("https://image", "https://image", "https://image", "https://image", "https://image", "https://image"))
                .body("reviews.member.profileUrl", contains("github.com", "github.com", "github.com", "github.com", "github.com", "github.com"))
                .body("reviews.createdDate", contains("2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08"))
                .body("reviews.lastModifiedDate", contains("2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08", "2021-11-08"))
                .body("totalResults", is(7));
    }
}
