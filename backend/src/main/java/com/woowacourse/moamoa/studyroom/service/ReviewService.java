package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.repository.review.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.review.Review;
import com.woowacourse.moamoa.studyroom.domain.review.Reviewer;
import com.woowacourse.moamoa.studyroom.domain.review.exception.WritingReviewBadRequestException;
import com.woowacourse.moamoa.studyroom.service.exception.review.ReviewNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.review.EditingReviewRequest;
import com.woowacourse.moamoa.studyroom.service.request.review.WriteReviewRequest;
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

        if (!study.isReviewWritable(member.getId())) {
            throw new WritingReviewBadRequestException();
        }

        final Review review = Review.writeNewReview(study.getId(), member.getId(), writeReviewRequest.getContent());
        return reviewRepository.save(review).getId();
    }

    public void updateReview(final Long githubId, final Long reviewId, final EditingReviewRequest editingReviewRequest) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.updateContent(new Reviewer(member.getId()), editingReviewRequest.getContent());
    }

    public void deleteReview(final Long githubId, final Long reviewId) {
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(MemberNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.delete(new Reviewer(member.getId()));
    }
}
