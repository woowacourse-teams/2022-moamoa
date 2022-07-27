package com.woowacourse.moamoa.review.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ReviewsResponse> getReviews(
            @PathVariable(name = "study-id") final Long studyId,
            @RequestParam(required = false) final Integer size
    ) {
        final ReviewsResponse reviewsResponse = reviewService.getReviewsByStudy(studyId, size);
        return ResponseEntity.ok(reviewsResponse);
    }

    @PostMapping
    public ResponseEntity<Void> writeReview(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable(name = "study-id") final Long studyId,
            @Valid @RequestBody final WriteReviewRequest writeReviewRequest
    ) {
        final Long id = reviewService.writeReview(githubId, studyId, writeReviewRequest);
        return ResponseEntity.created(URI.create("/api/studies/" + studyId + "/reviews/" + id)).build();
    }
}
