package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice")
@Where(clause = "deleted = false")
public class NoticeArticle extends BaseEntity {

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

    @Embedded
    private NoticeContent content;

    public static NoticeArticle create(final StudyRoom studyRoom, final Accessor accessor, final NoticeContent content) {
        if (studyRoom.isOwner(accessor)) {
            return new NoticeArticle(accessor.getMemberId(), studyRoom, content);
        }

        throw new UneditableArticleException(studyRoom.getId(), accessor, NoticeArticle.class);
    }

    NoticeArticle(final Long authorId, final StudyRoom studyRoom, final NoticeContent content) {
        this(null, authorId, studyRoom, content);
    }

    private NoticeArticle(final Long id, final Long authorId,
                         final StudyRoom studyRoom, final NoticeContent content) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.authorId = authorId;
        this.content = content;
    }

    public final void delete(final Accessor accessor) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        deleted = true;
    }

    public void update(final Accessor accessor, final NoticeContent content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        this.content = content;
    }

    public Long getId() {
        return id;
    }

    boolean isDeleted() {
        return deleted;
    }

    protected boolean isUneditableAccessor(final Accessor accessor) {
        return !studyRoom.isOwner(accessor);
    }

    public NoticeContent getContent() {
        return content;
    }
}
