package com.woowacourse.moamoa.studyroom.domain.link;

import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class LinkContent {

    @Lob
    @Column(nullable = false)
    private String linkUrl;

    @Lob
    private String description;

    public LinkContent(final String linkUrl, final String description) {
        this.linkUrl = linkUrl;
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LinkContent that = (LinkContent) o;
        return Objects.equals(linkUrl, that.linkUrl) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkUrl, description);
    }
}
