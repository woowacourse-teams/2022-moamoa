package com.woowacourse.moamoa.review.controller;

import static com.woowacourse.fixtures.MemberFixtures.짱구;
import static com.woowacourse.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰1;
import static com.woowacourse.fixtures.ReviewFixtures.자바_리뷰1_아이디;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.EditReviewRequest;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class ReviewControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        reviewController = new ReviewController(new ReviewService(reviewRepository, memberRepository, studyRepository));

        memberRepository.save(짱구);
        studyRepository.save(자바_스터디);
        reviewRepository.save(자바_리뷰1);
    }

    @DisplayName("정상적으로 리뷰 수정이 되었는지 확인한다.")
    @Test
    void updateReview() {
        final EditReviewRequest editReviewRequest = new EditReviewRequest("수정한 리뷰입니다.");
        reviewController.updateReview(짱구_깃허브_아이디, 자바_리뷰1_아이디, editReviewRequest);

        final Review updatedReview = reviewRepository.findById(자바_리뷰1_아이디).get();

        assertThat(updatedReview.getContent()).isEqualTo(editReviewRequest.getContent());
    }
}
