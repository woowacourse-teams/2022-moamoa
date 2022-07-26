package com.woowacourse.moamoa.review.controller;

import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.SizeRequest;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{study-id}/reviews")
    public ResponseEntity<ReviewsResponse> getReviews(
            @PathVariable(name = "study-id") Long studyId,
            @RequestParam(name = "size", required = false, defaultValue = "") SizeRequest sizeRequest
    ) {
        final ReviewsResponse reviewsResponse = reviewService.getReviewsByStudy(studyId, sizeRequest);
        return ResponseEntity.ok(reviewsResponse);
    }
}
