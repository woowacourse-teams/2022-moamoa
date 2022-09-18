package com.woowacourse.moamoa.studyroom.controller.review;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.review.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.ReviewService;
import com.woowacourse.moamoa.studyroom.domain.review.exception.UnwrittenReviewException;
import com.woowacourse.moamoa.studyroom.service.request.review.EditingReviewRequest;
import com.woowacourse.moamoa.studyroom.service.request.review.WriteReviewRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.controller.ReviewController;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReviewControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewController sut;

    private Long 자바_스터디_아이디;
    private Long 짱구_리뷰;

    private Member 짱구;
    private Member 그린론;

    @BeforeEach
    void setUp() {
        sut = new ReviewController(new ReviewService(reviewRepository, memberRepository, studyRoomRepository));

        // 사용자 추가
        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = StudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        Study javaStudy = studyService.createStudy(짱구.getId(), javaStudyRequest);
        자바_스터디_아이디 = javaStudy.getId();

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository);
        participantService.participateStudy(그린론.getId(), javaStudy.getId());

        // 리뷰 추가
        ReviewService reviewService = new ReviewService(reviewRepository, memberRepository, studyRoomRepository);

        짱구_리뷰 = reviewService
                .writeReview(짱구.getId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용1"));

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("내가 작성하지 않은 리뷰를 수정할 수 없다.")
    @Test
    void notUpdate() {
        final EditingReviewRequest request = new EditingReviewRequest("수정한 리뷰 내용입니다.");
        final Long memberId = 그린론.getId();

        assertThatThrownBy(() -> sut.updateReview(memberId, 자바_스터디_아이디, 짱구_리뷰, request))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("내가 작성하지 않은 리뷰를 삭제할 수 없다.")
    @Test
    void notDelete() {
        final Long memberId = 그린론.getId();

        assertThatThrownBy(() -> sut.deleteReview(memberId, 자바_스터디_아이디, 짱구_리뷰))
                .isInstanceOf(UnwrittenReviewException.class);
    }
}
