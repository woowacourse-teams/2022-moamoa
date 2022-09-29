package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.repository.review.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.domain.review.Review;
import com.woowacourse.moamoa.studyroom.service.exception.review.ReviewNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.review.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StudyRoomRepository studyRoomRepository;

    public Long writeReview(final Long memberId, final Long studyId, final ReviewRequest reviewRequest) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);

        final Accessor accessor = new Accessor(memberId, studyId);
        final Review review = studyRoom.writeReview(accessor, reviewRequest.getContent());

        return reviewRepository.save(review).getId();
    }

    public void updateReview(
            final Long memberId,
            final Long studyId,
            final Long reviewId,
            final ReviewRequest reviewRequest
    ) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.updateContent(new Accessor(memberId, studyId), reviewRequest.getContent());
    }

    public void deleteReview(final Long memberId, final Long studyId, final Long reviewId) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.delete(new Accessor(memberId, studyId));
    }
}
