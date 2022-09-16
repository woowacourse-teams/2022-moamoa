package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
@Where(clause = "deleted = false")
public class NoticeArticle extends Article<NoticeContent> {

    @Embedded
    private NoticeContent content;

    NoticeArticle(final Long authorId, final StudyRoom studyRoom, final NoticeContent content) {
        this(null, authorId, studyRoom, content);
    }

    private NoticeArticle(final Long id, final Long authorId,
                         final StudyRoom studyRoom, final NoticeContent content) {
        super(id, studyRoom, authorId);
        this.content = content;
    }

    @Override
    protected void updateContent(final NoticeContent content) {
        this.content = content;
    }

    @Override
    protected boolean isEditableAccessor(final Accessor accessor) {
        return studyRoom.isOwner(accessor);
    }

    @Override
    public NoticeContent getContent() {
        return content;
    }


}
