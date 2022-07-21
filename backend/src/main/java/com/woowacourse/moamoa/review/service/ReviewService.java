package com.woowacourse.moamoa.review.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewsResponse getReviewsByStudy(Long studyId) {
        final AssociatedStudy associatedStudy = new AssociatedStudy(studyId);

        final List<Review> reviews = reviewRepository.findAllByAssociatedStudy(associatedStudy);

        return makeReviewsResponse(reviews);
    }

    private ReviewsResponse makeReviewsResponse(final List<Review> reviews) {
        final List<ReviewResponse> reviewResponses = reviews.stream()
                .map(review -> new ReviewResponse(review))
                .collect(toList());

        return new ReviewsResponse(reviewResponses, reviews.size());
    }
}
