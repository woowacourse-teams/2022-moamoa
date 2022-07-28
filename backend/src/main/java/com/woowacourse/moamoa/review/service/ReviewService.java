package com.woowacourse.moamoa.review.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.MemberDao;
import com.woowacourse.moamoa.member.service.exception.InvalidMemberException;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.exception.InvalidReviewException;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final MemberDao memberDao;

    public ReviewsResponse getReviewsByStudy(Long studyId, Integer size) {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);

        final List<Review> reviews = reviewRepository.findAllByAssociatedStudy(associatedStudy);

        if (size != null) {
            return makeReviewsResponse(reviews.subList(0, size), reviews.size());
        }

        return makeReviewsResponse(reviews, reviews.size());
    }

    private ReviewsResponse makeReviewsResponse(final List<Review> reviews, final int totalCount) {
        final List<ReviewResponse> reviewResponses = reviews.stream()
                .map(ReviewResponse::new)
                .collect(toList());

        return new ReviewsResponse(reviewResponses, totalCount);
    }

    @Transactional
    public Long writeReview(final Long githubId, final Long studyId, final WriteReviewRequest writeReviewRequest) {
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Member member = memberRepository.findByGithubId(githubId)
                .orElseThrow(InvalidMemberException::new);
        final Participant participant = new Participant(member.getId());

        if (!study.isParticipant(participant)) {
            throw new NotParticipatedMemberException();
        }

        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);
        final Review review = writeReviewRequest.toReview(associatedStudy, member);

        if (isReviewIsBeforeThanStudyStartDate(review, study)) {
            throw new InvalidReviewException();
        }

        return reviewRepository.save(review).getId();
    }

    private boolean isReviewIsBeforeThanStudyStartDate(final Review review, final Study study) {
        final LocalDate createdDate = review.getCreatedDate().toLocalDate();
        final LocalDate studyStartDate = study.getPeriod().getStartDate();

        return createdDate.isBefore(studyStartDate);
    }
}
