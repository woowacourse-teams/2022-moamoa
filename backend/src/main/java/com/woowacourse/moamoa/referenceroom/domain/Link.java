package com.woowacourse.moamoa.referenceroom.domain;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.referenceroom.service.exception.UnwrittenLinkException;
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

    public Link(
            final AssociatedStudy associatedStudy, final Author author, final String linkUrl, final String description
    ) {
        this(null, associatedStudy, author, linkUrl, description);
    }

    public void update(final Link updatedLink) {
        validateAuthor(updatedLink.getAuthor());

        linkUrl = updatedLink.linkUrl;
        description = updatedLink.description;
    }

    private void validateAuthor(final Author author) {
        if (!this.author.equals(author)) {
            throw new UnwrittenLinkException();
        }
    }
}
