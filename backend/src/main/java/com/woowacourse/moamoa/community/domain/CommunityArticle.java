package com.woowacourse.moamoa.community.domain;

import com.woowacourse.moamoa.study.domain.Study;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community")
public class CommunityArticle extends Article {

    CommunityArticle(final String title, final String content, final Long authorId, final Study study) {
        super(null, title, content, authorId, study);
    }

    public CommunityArticle(final Long id, final String title, final String content, final Long authorId,
                            final Study study) {
        super(id, title, content, authorId, study);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommunityArticle that = (CommunityArticle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthorId(),
                that.getAuthorId()) && Objects.equals(getStudy().getId(), that.getStudy().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudy().getId());
    }
}
