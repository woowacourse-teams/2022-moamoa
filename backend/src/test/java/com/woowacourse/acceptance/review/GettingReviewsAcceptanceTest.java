package com.woowacourse.acceptance.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
import com.woowacourse.moamoa.study.service.CreateStudyService;
import com.woowacourse.moamoa.study.service.request.CreateStudyRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class GettingReviewsAcceptanceTest extends AcceptanceTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<ReviewResponse> javaReviews;

    @BeforeEach
    void initDataBase() {
        final String jjangguToken = getBearerTokenBySignInOrUp(toGithubProfileResponse(JJANGGU));
        getBearerTokenBySignInOrUp(toGithubProfileResponse(GREENLAWN));
        getBearerTokenBySignInOrUp(toGithubProfileResponse(DWOO));
        getBearerTokenBySignInOrUp(toGithubProfileResponse(VERUS));

        final LocalDate startDate = LocalDate.now();
        CreateStudyRequest javaStudyRequest = CreateStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreateStudyRequest reactStudyRequest = CreateStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        long javaStudyId = createStudy(jjangguToken, javaStudyRequest);
        long reactStudyId = createStudy(jjangguToken, reactStudyRequest);

        final LocalDate createdAt = startDate.plusDays(1);
        final LocalDate lastModifiedDate = startDate.plusDays(2);

        // 리뷰 추가
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (1, " + javaStudyId + ", " + 1L + ", '리뷰 내용1', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (2, " + javaStudyId + ", " + 2L + ", '리뷰 내용2', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (3, " + javaStudyId + ", " + 3L+ ", '리뷰 내용3', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (4, " + javaStudyId + ", " + 4L + ", '리뷰 내용4', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");
        jdbcTemplate.update("INSERT INTO review(id, study_id, member_id, content, created_date, last_modified_date) "
                + "VALUES (5, " + reactStudyId + ", " + 1L + ", '리뷰 내용5', '"
                + createdAt.toString() + "T11:23:30.123456', '" + lastModifiedDate.toString()+ "T11:45:20.456123')");

        javaReviews = List.of(
                new ReviewResponse(1L, new WriterResponse(JJANGGU), createdAt, lastModifiedDate, "리뷰 내용1"),
                new ReviewResponse(2L, new WriterResponse(GREENLAWN), createdAt, lastModifiedDate, "리뷰 내용2"),
                new ReviewResponse(3L, new WriterResponse(DWOO), createdAt, lastModifiedDate, "리뷰 내용3"),
                new ReviewResponse(4L, new WriterResponse(VERUS), createdAt, lastModifiedDate, "리뷰 내용4")
        );
    }

    private static GithubProfileResponse toGithubProfileResponse(MemberData memberData) {
        return new GithubProfileResponse(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("스터디에 달린 전체 리뷰 목록을 조회할 수 있다.")
    @Test
    void getAllReviews() {
        final ReviewsResponse reviewsResponse = RestAssured.given().log().all()
                .when().log().all()
                .get("api/studies/1/reviews")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);

        assertThat(reviewsResponse.getTotalResults()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews())
                .containsExactlyInAnyOrderElementsOf(javaReviews);
    }

    @DisplayName("원하는 갯수만큼 스터디에 달린 리뷰 목록을 조회할 수 있다.")
    @Test
    public void getReviewsBySize() {
        final ReviewsResponse reviewsResponse = RestAssured.given().log().all()
                .when().log().all()
                .get("/api/studies/1/reviews?size=2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);

        assertThat(reviewsResponse.getTotalResults()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews.subList(0, 2));
    }
}
