package com.woowacourse.acceptance.test.review;

import static com.woowacourse.acceptance.fixture.MemberFixture.그린론_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixture.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixture.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.그린론_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.디우_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixture.디우_이름;
import static com.woowacourse.acceptance.fixture.MemberFixture.디우_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.디우_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_이름;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.베루스_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.짱구_깃허브_ID;
import static com.woowacourse.acceptance.fixture.MemberFixture.짱구_이름;
import static com.woowacourse.acceptance.fixture.MemberFixture.짱구_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixture.짱구_프로필_URL;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.review.service.request.EditingReviewRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
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

    private Long 자바_스터디;
    private Long 짱구_자바_리뷰;
    private Long 그린론_자바_리뷰;
    private List<ReviewResponse> 자바_리뷰들;

    @BeforeEach
    void setUp() {
        final LocalDate 지금 = LocalDate.now();

        자바_스터디 = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 리액트 = 짱구가().로그인하고().리액트_스터디를().시작일자는(지금).생성한다();

        그린론이().로그인하고().스터디에(자바_스터디).참여한다();
        디우가().로그인하고().스터디에(자바_스터디).참여한다();
        베루스가().로그인하고().스터디에(자바_스터디).참여한다();

        짱구_자바_리뷰 = 짱구가().로그인하고().스터디에(자바_스터디).리뷰를_작성한다("리뷰 내용1");
        그린론_자바_리뷰 = 그린론이().로그인하고().스터디에(자바_스터디).리뷰를_작성한다("리뷰 내용2");
        long 디우_자바_리뷰 = 디우가().로그인하고().스터디에(자바_스터디).리뷰를_작성한다("리뷰 내용3");
        long 베루스_자바_리뷰 = 베루스가().로그인하고().스터디에(자바_스터디).리뷰를_작성한다("리뷰 내용4");

        짱구가().로그인하고().스터디에(리액트).리뷰를_작성한다("리뷰 내용5");

        final LocalDate 리뷰_생성일 = 지금;
        final LocalDate 리뷰_수정일 = 지금;

        final WriterResponse 짱구 = new WriterResponse(짱구_깃허브_ID, 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final ReviewResponse 짱구_리뷰 = new ReviewResponse(짱구_자바_리뷰, 짱구, 리뷰_생성일, 리뷰_수정일, "리뷰 내용1");

        final WriterResponse 그린론 = new WriterResponse(그린론_깃허브_ID, 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL);
        final ReviewResponse 그린론_리뷰 = new ReviewResponse(그린론_자바_리뷰, 그린론, 리뷰_생성일, 리뷰_수정일, "리뷰 내용2");

        final WriterResponse 디우 = new WriterResponse(디우_깃허브_ID, 디우_이름, 디우_이미지_URL, 디우_프로필_URL);
        final ReviewResponse 디우_리뷰 = new ReviewResponse(디우_자바_리뷰, 디우, 리뷰_생성일, 리뷰_수정일, "리뷰 내용3");

        final WriterResponse 베루스 = new WriterResponse(베루스_깃허브_ID, 베루스_이름, 베루스_이미지_URL, 베루스_프로필_URL);
        final ReviewResponse 베루스_리뷰 = new ReviewResponse(베루스_자바_리뷰, 베루스, 리뷰_생성일, 리뷰_수정일, "리뷰 내용4");

        자바_리뷰들 = List.of(베루스_리뷰, 디우_리뷰, 그린론_리뷰, 짱구_리뷰);
    }

    @DisplayName("리뷰를 작성한다.")
    @Test
    void create() {
        final String token = 짱구가().로그인한다();
        final WriteReviewRequest writeReviewRequest = new WriteReviewRequest("짱구의 스터디 리뷰입니다.");

        RestAssured.given(spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디)
                .body(writeReviewRequest)
                .filter(document("reviews/create"))
                .when().log().all()
                .post("/api/studies/{study-id}/reviews")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("스터디에 달린 전체 리뷰 목록을 조회할 수 있다.")
    @Test
    void getAllReviews() {
        final ReviewsResponse reviewsResponse = RestAssured.given(spec).log().all()
                .filter(document("reviews/list"))
                .when().log().all()
                .get("api/studies/1/reviews")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews())
                .containsExactlyInAnyOrderElementsOf(자바_리뷰들);
    }

    @DisplayName("원하는 갯수만큼 스터디에 달린 리뷰 목록을 조회할 수 있다.")
    @Test
    public void getReviewsBySize() {
        final ReviewsResponse reviewsResponse = RestAssured.given(spec).log().all()
                .filter(document("reviews/list-certain-number"))
                .when().log().all()
                .get("/api/studies/1/reviews?size=2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReviewsResponse.class);

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews()).containsExactlyInAnyOrderElementsOf(자바_리뷰들.subList(0, 2));
    }

    @DisplayName("자신이 참여한 스터디에 작성한 리뷰를 삭제할 수 있다.")
    @Test
    void deleteReview() {
        final String token = 짱구가().로그인한다();

        RestAssured.given(spec).log().all()
                .filter(document("reviews/delete"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디)
                .pathParam("review-id", 짱구_자바_리뷰)
                .when().log().all()
                .delete("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("자신이 참여한 스터디에 작성한 리뷰를 수정할 수 있다.")
    @Test
    void updateReview() {
        final String token = 짱구가().로그인한다();
        final EditingReviewRequest request = new EditingReviewRequest("edit review");

        RestAssured.given(spec).log().all()
                .filter(document("reviews/update"))
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .pathParam("study-id", 자바_스터디)
                .pathParam("review-id", 짱구_자바_리뷰)
                .body(request)
                .when().log().all()
                .put("/api/studies/{study-id}/reviews/{review-id}")
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
