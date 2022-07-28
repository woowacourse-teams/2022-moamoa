package com.woowacourse.moamoa.review.controller;

import com.woowacourse.moamoa.review.service.SearchingReviewService;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reviews")
@RequiredArgsConstructor
public class SearchingReviewController {

    private final SearchingReviewService searchingReviewService;

    @GetMapping
    public ResponseEntity<ReviewsResponse> getReviews(
            @PathVariable(name = "study-id") final Long studyId,
            @RequestParam(required = false) final Integer size
    ) {
        final ReviewsResponse reviewsResponse = searchingReviewService.getReviewsByStudy(studyId, size);
        return ResponseEntity.ok(reviewsResponse);
    }
}
