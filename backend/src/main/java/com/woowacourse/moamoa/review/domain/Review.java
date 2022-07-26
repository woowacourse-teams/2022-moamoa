package com.woowacourse.moamoa.review.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.member.domain.Member;
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

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Review extends BaseTime {

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

    public void writeable(final LocalDateTime studyStartDate) {
        final LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(studyStartDate)) {
            throw new BadRequestException();
        }
    }
}
