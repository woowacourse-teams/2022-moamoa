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
public class LinkArticle extends BaseEntity implements Article<LinkContent>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    @Column(name = "member_id", nullable = false)
    private Long authorId;

    @Embedded
    private LinkContent content;

    @Column(nullable = false)
    private boolean deleted;

    LinkArticle(final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        this(null, studyRoom, authorId, content);
    }

    private LinkArticle(final Long id, final StudyRoom studyRoom, final Long authorId, final LinkContent content) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.authorId = authorId;
        this.content = content;
        this.deleted = false;
    }

    public void update(final Accessor accessor, final LinkContent content) {
        if (!studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId())) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.LINK);
        }

        this.content = content;
    }

    @Override
    public void delete(final Accessor accessor) {
        if (!studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId())) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.LINK);
        }

        deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getLinkUrl() {
        return content.getLinkUrl();
    }

    public String getDescription() {
        return content.getDescription();
    }

    public boolean isDeleted() {
        return deleted;
    }
}
