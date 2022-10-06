package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.ReviewData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewsResponse {

    private List<ReviewResponse> reviews;
    private Integer totalCount;

    public ReviewsResponse(final List<ReviewData> reviews, final Integer totalCount) {
        this.reviews = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        this.totalCount = totalCount;
    }

    public ReviewsResponse(final List<ReviewData> reviews) {
        this.reviews = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        totalCount = reviews.size();
    }
}
