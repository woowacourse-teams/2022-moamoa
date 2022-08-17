package com.woowacourse.moamoa.studyroom.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "community")
public class CommunityArticle extends Article {

    private String title;

    private String content;

    CommunityArticle(final String title, final String content, final Long authorId,
                     final StudyRoom studyRoom) {
        super(null, authorId, studyRoom);
        this.title = title;
        this.content = content;
    }

    public CommunityArticle(final Long id, final String title, final String content, final Long authorId,
                            final StudyRoom studyRoom) {
        super(id, authorId, studyRoom);
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean isViewableBy(final Accessor accessor) {
        return isPermittedAccessor(accessor);
    }

    @Override
    public boolean isEditableBy(final Accessor accessor) {
        return isPermittedAccessor(accessor) && isAuthor(accessor);
    }

    @Override
    public void update(final Accessor accessor, final String title, final String content) {
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
        final CommunityArticle that = (CommunityArticle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getContent(), that.getContent()) && Objects.equals(getAuthorId(),
                that.getAuthorId()) && Objects.equals(getStudyRoom(), that.getStudyRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getAuthorId(), getStudyRoom());
    }
}
