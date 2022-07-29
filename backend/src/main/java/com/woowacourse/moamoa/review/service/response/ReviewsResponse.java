package com.woowacourse.moamoa.review.service.response;

import com.woowacourse.moamoa.review.query.data.ReviewData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewsResponse {

    private List<ReviewResponse> reviews;
    private Integer totalResults;

    public ReviewsResponse(final List<ReviewData> reviews, final Integer totalResults) {
        this.reviews = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        this.totalResults = totalResults;
    }

    public ReviewsResponse(final List<ReviewData> reviews) {
        this.reviews = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        totalResults = reviews.size();
    }
}
