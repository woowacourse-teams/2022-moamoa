package com.woowacourse.moamoa.review.service;

import com.woowacourse.moamoa.review.query.ReviewDao;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import com.woowacourse.moamoa.review.service.request.SizeRequest;
import com.woowacourse.moamoa.review.service.response.ReviewsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchingReviewService {

    private final ReviewDao reviewDao;

    public ReviewsResponse getReviewsByStudy(Long studyId, SizeRequest sizeRequest) {
        final List<ReviewData> allReviews = reviewDao.findAllByStudyId(studyId);

        if (sizeRequest.isEmpty() || sizeRequest.isMoreThan(allReviews.size())) {
            return new ReviewsResponse(allReviews);
        }

        return new ReviewsResponse(allReviews.subList(0, sizeRequest.getValue()), allReviews.size());
    }
}
