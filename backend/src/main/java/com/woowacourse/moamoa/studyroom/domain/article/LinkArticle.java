package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "link")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
public class LinkArticle extends Article<LinkContent>{

    @Embedded
    private LinkContent content;

    LinkArticle(final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        this(null, studyRoom, authorId, content);
    }

    private LinkArticle(final Long id, final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        super(id, studyRoom, authorId);
        this.content = content;
    }

    @Override
    public void update(final Accessor accessor, final LinkContent content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        this.content = content;
    }

    @Override
    protected boolean isUneditableAccessor(final Accessor accessor) {
        return !studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId());
    }

    public LinkContent getContent() {
        return content;
    }
}
