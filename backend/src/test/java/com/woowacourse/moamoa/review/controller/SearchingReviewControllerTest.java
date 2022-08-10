package com.woowacourse.moamoa.review.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.query.ReviewDao;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.SearchingReviewService;
import com.woowacourse.moamoa.review.service.request.SizeRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class SearchingReviewControllerTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ReviewRepository reviewRepository;

    private SearchingReviewController sut;

    private Study javaStudy;

    private List<ReviewResponse> javaReviews;

    @BeforeEach
    void setUp() {
        sut = new SearchingReviewController(new SearchingReviewService(reviewDao));

        // 사용자 추가
        final Member jjanggu = memberRepository.save(toMember(JJANGGU));
        final Member greenlawn = memberRepository.save(toMember(GREENLAWN));
        final Member dwoo = memberRepository.save(toMember(DWOO));
        final Member verus = memberRepository.save(toMember(VERUS));

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();
        CreatingStudyRequest reactStudyRequest = CreatingStudyRequest.builder()
                .title("react 스터디").excerpt("리액트 설명").thumbnail("react image").description("리액트 소개")
                .startDate(startDate)
                .build();

        javaStudy = studyService.createStudy(1L, javaStudyRequest);
        final Study reactStudy = studyService.createStudy(1L, reactStudyRequest);
        
        studyService.participateStudy(greenlawn.getGithubId(), javaStudy.getId());
        studyService.participateStudy(dwoo.getGithubId(), javaStudy.getId());
        studyService.participateStudy(verus.getGithubId(), javaStudy.getId());

        // 리뷰 추가
        ReviewService reviewService = new ReviewService(reviewRepository, memberRepository, studyRepository);

        final Long javaReviewId1 = reviewService
                .writeReview(jjanggu.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용1"));
        final Long javaReviewId2 = reviewService
                .writeReview(greenlawn.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용2"));
        final Long javaReviewId3 = reviewService
                .writeReview(dwoo.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용3"));
        final Long javaReviewId4 = reviewService
                .writeReview(verus.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용4"));
        reviewService.writeReview(jjanggu.getGithubId(), reactStudy.getId(), new WriteReviewRequest("리뷰 내용5"));

        final ReviewResponse 리뷰_내용1 = new ReviewResponse(javaReviewId1, new WriterResponse(JJANGGU), LocalDate.now(),
                LocalDate.now(), "리뷰 내용1");
        final ReviewResponse 리뷰_내용2 = new ReviewResponse(javaReviewId2, new WriterResponse(GREENLAWN), LocalDate.now(),
                LocalDate.now(), "리뷰 내용2");
        final ReviewResponse 리뷰_내용3 = new ReviewResponse(javaReviewId3, new WriterResponse(DWOO), LocalDate.now(),
                LocalDate.now(), "리뷰 내용3");
        final ReviewResponse 리뷰_내용4 = new ReviewResponse(javaReviewId4, new WriterResponse(VERUS), LocalDate.now(),
                LocalDate.now(), "리뷰 내용4");
        javaReviews = List.of(리뷰_내용4, 리뷰_내용3, 리뷰_내용2, 리뷰_내용1);

        entityManager.flush();
        entityManager.clear();
    }

    private static Member toMember(MemberData memberData) {
        return new Member(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("스터디의 전체 후기를 조회할 수 있다.")
    @Test
    public void getAllReviews() {
        final ResponseEntity<ReviewsResponse> reviewsResponse = sut.getReviews(javaStudy.getId(), SizeRequest.empty());

        assertThat(reviewsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reviewsResponse.getBody()).isNotNull();
        assertThat(reviewsResponse.getBody().getTotalCount()).isEqualTo(4);
        assertThat(reviewsResponse.getBody().getReviews()).containsExactlyInAnyOrderElementsOf(javaReviews);
    }

    @DisplayName("원하는 갯수 만큼 스터디의 후기를 조회할 수 있다.")
    @Test
    public void getReviewsByStudy() {
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
