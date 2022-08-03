package com.woowacourse.moamoa.review.domain.repository;

import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findByAssociatedStudyAndId(AssociatedStudy associatedStudy, Long id);

    void deleteById(Long id);

    @Query("SELECT review "
            + "FROM Review review "
            + "WHERE review.associatedStudy.studyId = :studyId AND review.id = :id")
    Optional<Review> findByStudyIdAndId(@Param("studyId") Long studyId, @Param("id") Long id);
}
