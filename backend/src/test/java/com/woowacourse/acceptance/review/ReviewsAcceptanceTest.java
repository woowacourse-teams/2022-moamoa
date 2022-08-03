package com.woowacourse.acceptance.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("리뷰 인수 테스트")
public class ReviewsAcceptanceTest extends AcceptanceTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

    private Long javaStudyId;
    private Long javaReviewId1;
    private Long javaReviewId2;

    private List<ReviewResponse> javaReviews;

    @BeforeEach
    void initDataBase() {
        final String jjangguToken = getBearerTokenBySignInOrUp(toGithubProfileResponse(JJANGGU));
        final String greenlawnToken = getBearerTokenBySignInOrUp(toGithubProfileResponse(GREENLAWN));
        final String dwooToken = getBearerTokenBySignInOrUp(toGithubProfileResponse(DWOO));
        final String verusToken = getBearerTokenBySignInOrUp(toGithubProfileResponse(VERUS));

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreatingStudyRequest reactStudyRequest = CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        javaStudyId = createStudy(jjangguToken, javaStudyRequest);
        long reactStudyId = createStudy(jjangguToken, reactStudyRequest);

        participateStudy(greenlawnToken, javaStudyId);
        participateStudy(dwooToken, javaStudyId);
        participateStudy(verusToken, javaStudyId);

        final LocalDate createdAt = LocalDate.now();
        final LocalDate lastModifiedDate = LocalDate.now();

        // 리뷰 추가
        javaReviewId1 = createReview(jjangguToken, javaStudyId, new WriteReviewRequest("리뷰 내용1"));
        javaReviewId2 = createReview(greenlawnToken, javaStudyId, new WriteReviewRequest("리뷰 내용2"));
        long javaReviewId3 = createReview(dwooToken, javaStudyId, new WriteReviewRequest("리뷰 내용3"));
        long javaReviewId4 = createReview(verusToken, javaStudyId, new WriteReviewRequest("리뷰 내용4"));
        createReview(jjangguToken, reactStudyId, new WriteReviewRequest("리뷰 내용5"));

        javaReviews = List.of(
                new ReviewResponse(javaReviewId1, new WriterResponse(JJANGGU), createdAt, lastModifiedDate, "리뷰 내용1"),
                new ReviewResponse(javaReviewId2, new WriterResponse(GREENLAWN), createdAt, lastModifiedDate, "리뷰 내용2"),
                new ReviewResponse(javaReviewId3, new WriterResponse(DWOO), createdAt, lastModifiedDate, "리뷰 내용3"),
                new ReviewResponse(javaReviewId4, new WriterResponse(VERUS), createdAt, lastModifiedDate, "리뷰 내용4")
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

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
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

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews.subList(0, 2));
    }

    @DisplayName("자신이 참여한 스터디에 작성한 리뷰를 삭제할 수 있다.")
    @Test
    void deleteReview() {
        final String token = getBearerTokenBySignInOrUp(toGithubProfileResponse(JJANGGU));

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", javaStudyId)
                .pathParam("review-id", javaReviewId1)
                .when().log().all()
                .delete("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("내가 작성하지 않은 리뷰를 삭제할 수 없다.")
    @Test
    void deleteNotWriteReview() {
        final String token = getBearerTokenBySignInOrUp(toGithubProfileResponse(JJANGGU));

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", javaStudyId)
                .pathParam("review-id", javaReviewId2)
                .when().log().all()
                .delete("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
