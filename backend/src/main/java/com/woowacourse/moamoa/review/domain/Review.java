package com.woowacourse.moamoa.review.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @Embedded
    private Reviewer reviewer;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    public Review(
            final AssociatedStudy associatedStudy, final Reviewer reviewer, final String content,
            final LocalDateTime createdDate, final LocalDateTime lastModifiedDate
    ) {
        this(null, associatedStudy, reviewer, content, createdDate, lastModifiedDate);
    }

    public static Review writeNewReview(Long studyId, Long memberId, String content, LocalDateTime createdDate) {
        return new Review(new AssociatedStudy(studyId), new Reviewer(memberId), content, createdDate, createdDate);
    }

    public Long getId() {
        return id;
    }
}
