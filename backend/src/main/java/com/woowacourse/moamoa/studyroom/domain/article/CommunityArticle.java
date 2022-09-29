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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community")
@Where(clause = "deleted = false")
public class CommunityArticle extends Article<CommunityContent> {

    @Embedded
    private CommunityContent content;

    CommunityArticle(final StudyRoom studyRoom, final Long authorId, final CommunityContent content) {
        this(null, authorId, studyRoom, content);
    }

    private CommunityArticle(final Long id, final Long authorId, final StudyRoom studyRoom,
                            final CommunityContent content
    ) {
        super(id, studyRoom, authorId);
        this.content = content;
    }

    @Override
    public void update(final Accessor accessor, final CommunityContent content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        this.content = content;
    }

    @Override
    protected boolean isUneditableAccessor(final Accessor accessor) {
        return !studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId());
    }

    public CommunityContent getContent() {
        return content;
    }
}
