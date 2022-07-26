package com.woowacourse.moamoa.review.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            @PathVariable(name = "study-id") final Long studyId,
            @RequestParam(required = false) final Integer size
    ) {
        final ReviewsResponse reviewsResponse = reviewService.getReviewsByStudy(studyId, size);

        return ResponseEntity.ok(reviewsResponse);
    }

    @PostMapping("/{study-id}/reviews")
    public ResponseEntity<?> writeReview(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable(name = "study-id") final Long studyId
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
    }
}
