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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted = false")
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

    @Column(nullable = false)
    private boolean deleted;

    public Link(
            final AssociatedStudy associatedStudy, final Author author, final String linkUrl, final String description
    ) {
        this(null, associatedStudy, author, linkUrl, description, false);
    }

    public void update(final Link updatedLink) {
        validateAuthor(updatedLink.author);

        linkUrl = updatedLink.linkUrl;
        description = updatedLink.description;
    }

    public void delete(final Author author) {
        validateAuthor(author);
        deleted = true;
    }

    private void validateAuthor(final Author author) {
        if (!this.author.equals(author)) {
            throw new UnwrittenLinkException();
        }
    }

    public Long getId() {
        return id;
    }

    public AssociatedStudy getAssociatedStudy() {
        return associatedStudy;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
