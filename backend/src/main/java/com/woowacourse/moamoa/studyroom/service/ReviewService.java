package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.review.Review;
import com.woowacourse.moamoa.studyroom.domain.review.repository.ReviewRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.exception.ReviewNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.StudyRoomNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudyRoomRepository studyRoomRepository;

    public Long writeReview(final Long memberId, final Long studyId, final ReviewRequest reviewRequest) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyRoomNotFoundException::new);

        final Accessor accessor = new Accessor(memberId, studyId);
        final Review review = Review.write(studyRoom, accessor, reviewRequest.getContent());

        return reviewRepository.save(review).getId();
    }

    public void updateReview(
            final Long memberId,
            final Long studyId,
            final Long reviewId,
            final ReviewRequest reviewRequest
    ) {
        studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyRoomNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.updateContent(new Accessor(memberId, studyId), reviewRequest.getContent());
    }

    public void deleteReview(final Long memberId, final Long studyId, final Long reviewId) {
        studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyRoomNotFoundException::new);
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.delete(new Accessor(memberId, studyId));
    }
}
