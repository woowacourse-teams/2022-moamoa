package com.woowacourse.moamoa.review.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.exception.WritingReviewBadRequestException;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.exception.ReviewNotFoundException;
import com.woowacourse.moamoa.review.service.exception.UnwrittenReviewException;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;

    public Long writeReview(final Long githubId, final Long studyId, final WriteReviewRequest writeReviewRequest) {
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);

        if (!study.isWritableReviews(member.getId())) {
            throw new WritingReviewBadRequestException();
        }

        final Review review = Review.writeNewReview(study.getId(), member.getId(), writeReviewRequest.getContent());
        return reviewRepository.save(review).getId();
    }

    public void deleteReview(final Long githubId, final Long reviewId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        if (!review.isReviewer(member.getId())) {
            throw new UnwrittenReviewException();
        }

        reviewRepository.deleteById(reviewId);
    }
}
