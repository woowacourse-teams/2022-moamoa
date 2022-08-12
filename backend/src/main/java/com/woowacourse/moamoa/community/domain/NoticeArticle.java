package com.woowacourse.moamoa.community.domain;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.study.domain.Study;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
public class NoticeArticle extends Article {

    NoticeArticle(final String title, final String content, final Long authorId, final Study study) {
        super(null, title, content, authorId, study);
    }

    public NoticeArticle(final Long id, final String title, final String content, final Long authorId,
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
        final NoticeArticle that = (NoticeArticle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthorId(),
                that.getAuthorId()) && Objects.equals(getStudy().getId(), that.getStudy().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudy().getId());
    }
}
