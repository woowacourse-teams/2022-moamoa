package com.woowacourse.moamoa.review.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.request.EditingReviewRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> writeReview(
            @AuthenticatedMember final Long memberId,
            @PathVariable(name = "study-id") final Long studyId,
            @Valid @RequestBody final WriteReviewRequest writeReviewRequest
    ) {
        final Long id = reviewService.writeReview(memberId, studyId, writeReviewRequest);
        return ResponseEntity.created(URI.create("/api/studies/" + studyId + "/reviews/" + id)).build();
    }

    @PutMapping("/{review-id}")
    public ResponseEntity<Void> updateReview(
            @AuthenticatedMember final Long memberId,
            @PathVariable(name = "review-id") final Long reviewId,
            @Valid @RequestBody final EditingReviewRequest editingReviewRequest
    ) {
        reviewService.updateReview(memberId, reviewId, editingReviewRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{review-id}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticatedMember final Long memberId,
            @PathVariable(name = "review-id") final Long reviewId
    ) {
        reviewService.deleteReview(memberId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
