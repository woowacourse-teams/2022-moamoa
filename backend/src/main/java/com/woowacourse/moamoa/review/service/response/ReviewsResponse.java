package com.woowacourse.moamoa.review.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewsResponse {

    private List<ReviewResponse> reviews;
    private Integer totalResults;
}
