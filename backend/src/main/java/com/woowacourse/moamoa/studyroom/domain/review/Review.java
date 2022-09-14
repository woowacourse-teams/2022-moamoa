package com.woowacourse.moamoa.studyroom.domain.review;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.review.exception.ReviewNotWrittenInTheStudyException;
import com.woowacourse.moamoa.studyroom.domain.review.exception.UnwrittenReviewException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE Review SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @Embedded
    private Reviewer reviewer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean deleted;

    public Review(
            final AssociatedStudy associatedStudy, final Reviewer reviewer, final String content
    ) {
        this(null, associatedStudy, reviewer, content, false);
    }

    public void updateContent(final AssociatedStudy associatedStudy, final Reviewer reviewer, final String content) {
        validateReview(associatedStudy, reviewer);
        this.content = content;
    }

    public void delete(final AssociatedStudy associatedStudy, final Reviewer reviewer) {
        validateReview(associatedStudy, reviewer);
        deleted = true;
    }

    private void validateReview(final AssociatedStudy associatedStudy, final Reviewer reviewer) {
        validateReviewWrittenInTheStudy(associatedStudy);
        validateReviewer(reviewer);
    }

    private void validateReviewWrittenInTheStudy(final AssociatedStudy associatedStudy) {
        if (!this.associatedStudy.equals(associatedStudy)) {
            throw new ReviewNotWrittenInTheStudyException();
        }
    }

    private void validateReviewer(final Reviewer reviewer) {
        if (!this.reviewer.equals(reviewer)) {
            throw new UnwrittenReviewException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
