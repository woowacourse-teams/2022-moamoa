package com.woowacourse.moamoa.referenceroom.domain;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AssociatedStudy associatedStudy;

    @Embedded
    private Author author;

    @Column(nullable = false)
    private String linkUrl;

    private String description;

    public Link(final Long studyId, final Long memberId, final String linkUrl, final String description) {
        this(null, new AssociatedStudy(studyId), new Author(memberId), linkUrl, description);
    }
}
