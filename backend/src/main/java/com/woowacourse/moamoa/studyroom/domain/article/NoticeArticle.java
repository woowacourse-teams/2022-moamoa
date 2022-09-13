package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notice")
public class NoticeArticle extends Article {

    private String title;

    private String content;

    public NoticeArticle(final String title, final String content, final Long authorId,
                  final StudyRoom studyRoom) {
        super(null, authorId, studyRoom);
        this.title = title;
        this.content = content;
    }

    public NoticeArticle(final Long id, final String title, final String content, final Long authorId,
                         final StudyRoom studyRoom) {
        super(id, authorId, studyRoom);
        this.title = title;
        this.content = content;
    }

    @Override
    public final boolean isViewableBy(final Accessor accessor) {
        return isPermittedAccessor(accessor);
    }

    @Override
    public final boolean isEditableBy(final Accessor accessor) {
        return isOwner(accessor);
    }

    @Override
    public final void update(final Accessor accessor, final String title, final String content) {
        if (!isEditableBy(accessor)) {
            throw new IllegalArgumentException();
        }

        this.title = title;
        this.content = content;
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
                that.getAuthorId()) && Objects.equals(getStudyRoom(), that.getStudyRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudyRoom());
    }
}
