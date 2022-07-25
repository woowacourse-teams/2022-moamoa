package com.woowacourse.moamoa.review.domain.repository;

import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import java.util.List;

public interface ReviewRepository {

    List<Review> findAllByAssociatedStudy(AssociatedStudy associatedStudy);
}