package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkContent {

    @Column(nullable = false)
    private String linkUrl;

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
