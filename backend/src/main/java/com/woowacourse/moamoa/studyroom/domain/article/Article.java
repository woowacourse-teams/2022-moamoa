package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Article<T extends Content<? extends Article<T>>> extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    protected StudyRoom studyRoom;

    @Column(name = "author_id", nullable = false)
    protected Long authorId;

    @Column(nullable = false)
    private boolean deleted;

    public Article(final Long id, final StudyRoom studyRoom, final Long authorId) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.authorId = authorId;
        this.deleted = false;
    }

    public final void update(final Accessor accessor, final T content) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.NOTICE);
        }

        updateContent(content);
    }

    public final void delete(final Accessor accessor) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    protected abstract void updateContent(final T content);

    protected abstract boolean isEditableAccessor(final Accessor accessor);

    public abstract T getContent();
}
