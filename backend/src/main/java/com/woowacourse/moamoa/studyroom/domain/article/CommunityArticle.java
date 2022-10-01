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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community")
@Where(clause = "deleted = false")
public class CommunityArticle extends BaseEntity {

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
    private CommunityContent content;

    public static CommunityArticle create(final StudyRoom studyRoom, final Accessor accessor, final CommunityContent content) {
        if (!studyRoom.isPermittedAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, CommunityArticle.class);
        }

        return new CommunityArticle(studyRoom, accessor.getMemberId(), content);
    }

    CommunityArticle(final StudyRoom studyRoom, final Long authorId, final CommunityContent content) {
        this(null, authorId, studyRoom, content);
    }

    private CommunityArticle(final Long id, final Long authorId, final StudyRoom studyRoom,
                            final CommunityContent content
    ) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
        this.content = content;
    }

    public void update(final Accessor accessor, final CommunityContent content) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        this.content = content;
    }

    public final void delete(final Accessor accessor) {
        if (isUneditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        deleted = true;
    }

    public Long getId() {
        return id;
    }

    boolean isDeleted() {
        return deleted;
    }

    protected boolean isUneditableAccessor(final Accessor accessor) {
        return !studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId());
    }

    public CommunityContent getContent() {
        return content;
    }
}
