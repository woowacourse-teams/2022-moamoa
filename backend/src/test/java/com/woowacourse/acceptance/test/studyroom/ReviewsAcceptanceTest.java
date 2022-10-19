package com.woowacourse.acceptance.test.studyroom;

import static com.woowacourse.acceptance.document.ReviewDocument.리뷰_목록_조회_문서;
import static com.woowacourse.acceptance.document.ReviewDocument.리뷰_삭제_문서;
import static com.woowacourse.acceptance.document.ReviewDocument.리뷰_생성_문서;
import static com.woowacourse.acceptance.document.ReviewDocument.리뷰_수정_문서;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.그린론_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.디우_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.베루스_프로필_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이름;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_이미지_URL;
import static com.woowacourse.acceptance.fixture.MemberFixtures.짱구_프로필_URL;
import static com.woowacourse.acceptance.steps.LoginSteps.그린론이;
import static com.woowacourse.acceptance.steps.LoginSteps.디우가;
import static com.woowacourse.acceptance.steps.LoginSteps.베루스가;
import static com.woowacourse.acceptance.steps.LoginSteps.짱구가;
import static org.assertj.core.api.Assertions.assertThat;

import com.slack.api.model.Attachment;
import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.alarm.request.SlackMessageRequest;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import com.woowacourse.moamoa.studyroom.service.response.ReviewResponse;
import com.woowacourse.moamoa.studyroom.service.response.ReviewsResponse;
import com.woowacourse.moamoa.studyroom.service.response.WriterResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("리뷰 인수 테스트")
class ReviewsAcceptanceTest extends AcceptanceTest {

    @DisplayName("리뷰를 작성한다.")
    @Test
    void create() {
        // arrange
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디 = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();

        // act
        final String content = "짱구의 스터디 리뷰입니다.";
        long 짱구_리뷰_ID = 짱구가().로그인하고().스터디에(자바_스터디).리뷰를().API_문서화를_하고(리뷰_생성_문서(spec)).작성한다(content);

        // assert
        final ReviewsResponse reviewsResponse = 짱구가().로그인하고().스터디에(자바_스터디)
                .리뷰를().API_문서화를_하고(리뷰_목록_조회_문서(spec)).목록_조회한다();

        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();
        final WriterResponse 짱구 = new WriterResponse(짱구_정보.getId(), 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final ReviewResponse 짱구_리뷰 = new ReviewResponse(짱구_리뷰_ID, 짱구, LocalDate.now(), LocalDate.now(), content);

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(1);
        assertThat(reviewsResponse.getReviews()).containsExactly(짱구_리뷰);
    }

    @DisplayName("스터디에 달린 전체 리뷰 목록을 조회할 수 있다.")
    @Test
    void getAllReviews() {
        // arrange
        final LocalDate 지금 = LocalDate.now();

        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();
        final MemberResponse 베루스_정보 = 베루스가().로그인하고().정보를_가져온다();
        final MemberResponse 그린론_정보 = 그린론이().로그인하고().정보를_가져온다();
        final MemberResponse 디우_정보 = 디우가().로그인하고().정보를_가져온다();

        long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 리액트_스터디_ID = 짱구가().로그인하고().리액트_스터디를().시작일자는(지금).생성한다();
        그린론이().로그인한다();
        디우가().로그인한다();
        베루스가().로그인한다();

        final SlackMessageRequest slackMessageRequest = new SlackMessageRequest("jjanggu",
                List.of(Attachment.builder().title("📚 스터디에 새로운 크루가 참여했습니다.")
                        .text("<https://moamoa.space/my/study/|모아모아 바로가기>")
                        .color("#36288f").build()));

        그린론이().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        디우가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();

        long 짱구_리뷰_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용1");
        long 그린론_리뷰_ID = 그린론이().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용2");
        long 디우_리뷰_ID = 디우가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용3");
        long 베루스_리뷰_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용4");
        짱구가().로그인하고().스터디에(리액트_스터디_ID).리뷰를().작성한다("리뷰 내용5");

        // act
        final ReviewsResponse reviewsResponse = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().목록_조회한다();

        // assert
        final LocalDate 리뷰_생성일 = 지금;
        final LocalDate 리뷰_수정일 = 지금;

        final WriterResponse 짱구 = new WriterResponse(짱구_정보.getId(), 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final ReviewResponse 짱구_리뷰 = new ReviewResponse(짱구_리뷰_ID, 짱구, 리뷰_생성일, 리뷰_수정일, "리뷰 내용1");

        final WriterResponse 그린론 = new WriterResponse(그린론_정보.getId(), 그린론_이름, 그린론_이미지_URL, 그린론_프로필_URL);
        final ReviewResponse 그린론_리뷰 = new ReviewResponse(그린론_리뷰_ID, 그린론, 리뷰_생성일, 리뷰_수정일, "리뷰 내용2");

        final WriterResponse 디우 = new WriterResponse(디우_정보.getId(), 디우_이름, 디우_이미지_URL, 디우_프로필_URL);
        final ReviewResponse 디우_리뷰 = new ReviewResponse(디우_리뷰_ID, 디우, 리뷰_생성일, 리뷰_수정일, "리뷰 내용3");

        final WriterResponse 베루스 = new WriterResponse(베루스_정보.getId(), 베루스_이름, 베루스_이미지_URL, 베루스_프로필_URL);
        final ReviewResponse 베루스_리뷰 = new ReviewResponse(베루스_리뷰_ID, 베루스, 리뷰_생성일, 리뷰_수정일, "리뷰 내용4");

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews())
                .containsExactlyInAnyOrderElementsOf(List.of(베루스_리뷰, 디우_리뷰, 그린론_리뷰, 짱구_리뷰));
    }

    @DisplayName("원하는 갯수만큼 스터디에 달린 리뷰 목록을 조회할 수 있다.")
    @Test
    void getReviewsBySize() {
        // arrange
        final LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 리액트_스터디_ID = 짱구가().로그인하고().리액트_스터디를().시작일자는(지금).생성한다();
        그린론이().로그인한다();
        디우가().로그인한다();
        베루스가().로그인한다();

        그린론이().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        디우가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();
        베루스가().로그인하고().스터디에(자바_스터디_ID).참여에_성공한다();

        짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용1");
        그린론이().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용2");
        long 디우_리뷰_ID = 디우가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용3");
        long 베루스_리뷰_ID = 베루스가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용4");
        짱구가().로그인하고().스터디에(리액트_스터디_ID).리뷰를().작성한다("리뷰 내용5");

        // act
        final ReviewsResponse reviewsResponse = 짱구가().로그인하고().스터디에(자바_스터디_ID)
                .리뷰를().목록_조회한다(2);

        // assert
        final LocalDate 리뷰_생성일 = 지금;
        final LocalDate 리뷰_수정일 = 지금;

        final MemberResponse 디우_정보 = 디우가().로그인하고().정보를_가져온다();
        final WriterResponse 디우 = new WriterResponse(디우_정보.getId(), 디우_이름, 디우_이미지_URL, 디우_프로필_URL);
        final ReviewResponse 디우_리뷰 = new ReviewResponse(디우_리뷰_ID, 디우, 리뷰_생성일, 리뷰_수정일, "리뷰 내용3");

        final MemberResponse 베루스_정보 = 베루스가().로그인하고().정보를_가져온다();
        final WriterResponse 베루스 = new WriterResponse(베루스_정보.getId(), 베루스_이름, 베루스_이미지_URL, 베루스_프로필_URL);
        final ReviewResponse 베루스_리뷰 = new ReviewResponse(베루스_리뷰_ID, 베루스, 리뷰_생성일, 리뷰_수정일, "리뷰 내용4");

        assertThat(reviewsResponse.getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getReviews()).containsExactlyInAnyOrder(디우_리뷰, 베루스_리뷰);
    }

    @DisplayName("자신이 참여한 스터디에 작성한 리뷰를 삭제할 수 있다.")
    @Test
    void deleteReview() {
        // arrange
        LocalDate 지금 = LocalDate.now();
        long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 짱구_리뷰_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용1");

        // act
        짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().API_문서화를_하고(리뷰_삭제_문서(spec)).삭제한다(짱구_리뷰_ID);

        // assert
        final ReviewsResponse response = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().목록_조회한다();
        assertThat(response.getReviews()).isEmpty();
        assertThat(response.getTotalCount()).isZero();
    }

    @DisplayName("자신이 참여한 스터디에 작성한 리뷰를 수정할 수 있다.")
    @Test
    void updateReview() {
        // arrange
        LocalDate 지금 = LocalDate.now();
        final MemberResponse 짱구_정보 = 짱구가().로그인하고().정보를_가져온다();
        long 자바_스터디_ID = 짱구가().로그인하고().자바_스터디를().시작일자는(지금).생성한다();
        long 짱구_리뷰_ID = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().작성한다("리뷰 내용1");

        // act
        ReviewRequest request = new ReviewRequest("수정 리뷰");
        짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().API_문서화를_하고(리뷰_수정_문서(spec)).수정한다(짱구_리뷰_ID, request);

        // assert
        final ReviewsResponse response = 짱구가().로그인하고().스터디에(자바_스터디_ID).리뷰를().목록_조회한다();

        final LocalDate 리뷰_생성일 = 지금;
        final LocalDate 리뷰_수정일 = 지금;
        final WriterResponse 짱구 = new WriterResponse(짱구_정보.getId(), 짱구_이름, 짱구_이미지_URL, 짱구_프로필_URL);
        final ReviewResponse 짱구_리뷰 = new ReviewResponse(짱구_리뷰_ID, 짱구, 리뷰_생성일, 리뷰_수정일, "수정 리뷰");
        assertThat(response.getReviews()).containsExactlyInAnyOrder(짱구_리뷰);
        assertThat(response.getTotalCount()).isEqualTo(1);
    }
}
