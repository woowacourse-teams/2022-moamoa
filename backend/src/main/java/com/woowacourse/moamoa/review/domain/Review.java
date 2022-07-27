package com.woowacourse.moamoa.review.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.review.domain.exception.InvalidReviewException;
import com.woowacourse.moamoa.study.domain.Study;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    @Embedded
//    private Reviewer reviewer;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    public static Review of(final AssociatedStudy associatedStudy, final Member member, final String content) {
        return new Review(associatedStudy, member, content);
    }

    public Review(final AssociatedStudy associatedStudy, final Member member, final String content) {
        this(null, associatedStudy, member, content);
    }

    public Review(final Long id, final AssociatedStudy associatedStudy, final Member member, final String content) {
        this.id = id;
        this.associatedStudy = associatedStudy;
        this.member = member;
        this.content = content;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void writeable(final Study study) {
        final LocalDate createdDate = this.createdDate.toLocalDate();
        final LocalDate studyStartDate = study.getPeriod().getStartDate();

        if (createdDate.isBefore(studyStartDate)) {
            throw new InvalidReviewException();
        }
    }
}
