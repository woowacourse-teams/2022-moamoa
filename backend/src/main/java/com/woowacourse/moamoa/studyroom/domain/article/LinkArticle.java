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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "link")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
public class LinkArticle extends BaseEntity {

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
    private LinkContent content;

    public static LinkArticle create(final StudyRoom studyRoom, final Accessor accessor, final LinkContent content) {
        if (studyRoom.isPermittedAccessor(accessor)) {
            return new LinkArticle(studyRoom, accessor.getMemberId(), content);
        }

        throw new UneditableArticleException(studyRoom.getId(), accessor, LinkArticle.class);
    }

    private LinkArticle(final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        this(null, studyRoom, authorId, content);
    }

    private LinkArticle(final Long id, final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.authorId = authorId;
        this.content = content;
    }

    public final void delete(final Accessor accessor) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, LinkArticle.class);
        }

        deleted = true;
    }

    public void update(final Accessor accessor, final LinkContent content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, LinkArticle.class);
        }

        this.content = content;
    }

    private boolean isUneditableAccessor(final Accessor accessor) {
        return !studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId());
    }

    public Long getId() {
        return id;
    }

    boolean isDeleted() {
        return deleted;
    }

    public LinkContent getContent() {
        return content;
    }
}
