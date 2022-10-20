package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_응답;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_신청서;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.controller.SearchingReviewController;
import com.woowacourse.moamoa.studyroom.domain.review.repository.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.ReviewDao;
import com.woowacourse.moamoa.studyroom.service.ReviewService;
import com.woowacourse.moamoa.studyroom.service.SearchingReviewService;
import com.woowacourse.moamoa.studyroom.service.request.SizeRequest;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import com.woowacourse.moamoa.studyroom.service.response.ReviewResponse;
import com.woowacourse.moamoa.studyroom.service.response.ReviewsResponse;
import com.woowacourse.moamoa.studyroom.service.response.WriterResponse;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class SearchingReviewControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private SearchingReviewController sut;

    private Study javaStudy;

    private List<ReviewResponse> javaReviews;

    @BeforeEach
    void setUp() {
        sut = new SearchingReviewController(new SearchingReviewService(reviewDao));

        // 사용자 추가
        final Long 짱구_아이디 = memberRepository.save(짱구()).getId();
        final Long 그린론_아이디 = memberRepository.save(그린론()).getId();
        final Long 디우_아이디 = memberRepository.save(디우()).getId();
        final Long 베루스_아이디 = memberRepository.save(베루스()).getId();

        // 짱구가 자바 스터디 생성하고 그린론, 디우, 베루스가 스터디 참여
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);
        StudyRequest reactStudyRequest = 리액트_스터디_신청서(startDate);

        javaStudy = studyService.createStudy(짱구_아이디, javaStudyRequest);
        final Study reactStudy = studyService.createStudy(짱구_아이디, reactStudyRequest);

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository, new DateTimeSystem());
        participantService.participateStudy(그린론_아이디, javaStudy.getId());
        participantService.participateStudy(디우_아이디, javaStudy.getId());
        participantService.participateStudy(베루스_아이디, javaStudy.getId());
        // 리뷰 추가
        ReviewService reviewService = new ReviewService(reviewRepository, studyRoomRepository);

        final Long javaReviewId1 = reviewService
                .writeReview(짱구_아이디, javaStudy.getId(), new ReviewRequest("리뷰 내용1"));
        final Long javaReviewId2 = reviewService
                .writeReview(그린론_아이디, javaStudy.getId(), new ReviewRequest("리뷰 내용2"));
        final Long javaReviewId3 = reviewService
                .writeReview(디우_아이디, javaStudy.getId(), new ReviewRequest("리뷰 내용3"));
        final Long javaReviewId4 = reviewService
                .writeReview(베루스_아이디, javaStudy.getId(), new ReviewRequest("리뷰 내용4"));
        reviewService.writeReview(짱구_아이디, reactStudy.getId(), new ReviewRequest("리뷰 내용5"));

        final ReviewResponse 리뷰_내용1 = new ReviewResponse(javaReviewId1, new WriterResponse(짱구_응답(짱구_아이디)),
                LocalDate.now(),
                LocalDate.now(), "리뷰 내용1");
        final ReviewResponse 리뷰_내용2 = new ReviewResponse(javaReviewId2, new WriterResponse(그린론_응답(그린론_아이디)),
                LocalDate.now(),
                LocalDate.now(), "리뷰 내용2");
        final ReviewResponse 리뷰_내용3 = new ReviewResponse(javaReviewId3, new WriterResponse(디우_응답(디우_아이디)),
                LocalDate.now(),
                LocalDate.now(), "리뷰 내용3");
        final ReviewResponse 리뷰_내용4 = new ReviewResponse(javaReviewId4, new WriterResponse(베루스_응답(베루스_아이디)),
                LocalDate.now(),
                LocalDate.now(), "리뷰 내용4");
        javaReviews = List.of(리뷰_내용4, 리뷰_내용3, 리뷰_내용2, 리뷰_내용1);

        entityManager.flush();
    }

    @DisplayName("스터디의 전체 후기를 조회할 수 있다.")
    @Test
    void getAllReviews() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), SizeRequest.empty());

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews);
    }

    @DisplayName("원하는 갯수 만큼 스터디의 후기를 조회할 수 있다.")
    @Test
    void getReviewsByStudy() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), new SizeRequest(2));

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews())
                .containsExactlyInAnyOrderElementsOf(javaReviews.subList(0, 2));
    }

    @DisplayName("원하는 갯수보다 후기가 적은 경우 나머지 후기를 조회한다.")
    @Test
    void getRemainReviews() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), new SizeRequest(5));

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews);
    }
}
