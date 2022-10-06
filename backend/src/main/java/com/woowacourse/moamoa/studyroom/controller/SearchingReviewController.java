package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.studyroom.service.SearchingReviewService;
import com.woowacourse.moamoa.studyroom.service.request.SizeRequest;
import com.woowacourse.moamoa.studyroom.service.response.ReviewsResponse;
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
            @PathVariable(name = "study-id") Long studyId,
            @RequestParam(name = "size", required = false, defaultValue = "") SizeRequest sizeRequest
    ) {
        final ReviewsResponse reviewsResponse = searchingReviewService.getReviewsByStudy(studyId, sizeRequest);
        return ResponseEntity.ok(reviewsResponse);
    }
}
