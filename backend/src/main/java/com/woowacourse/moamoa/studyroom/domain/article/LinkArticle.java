package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.service.exception.NotLinkAuthorException;
import com.woowacourse.moamoa.studyroom.service.exception.NotRelatedLinkException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import javax.persistence.Column;
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
    private StudyRoom studyRoom;

    @Column(name = "member_id", nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String linkUrl;

    private String description;

    @Column(nullable = false)
    private boolean deleted;

    public LinkArticle(final StudyRoom studyRoom, final Long authorId, final String linkUrl, final String description) {
        this(null, studyRoom, authorId, linkUrl, description, false);
    }

    private LinkArticle(final Long id, final StudyRoom studyRoom, final Long authorId, final String linkUrl,
                        final String description, final boolean deleted) {
        this.id = id;
        this.studyRoom = studyRoom;
        this.authorId = authorId;
        this.linkUrl = linkUrl;
        this.description = description;
        this.deleted = deleted;
    }

    public void update(final Accessor accessor, final String linkUrl, final String description) {
        if (!studyRoom.isPermittedAccessor(accessor) || !authorId.equals(accessor.getMemberId())) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.LINK);
        }

        this.linkUrl = linkUrl;
        this.description = description;
    }

    public void delete(Long authorId, Long studyId) {
        validateBelongToStudy(studyId);
        validateAuthor(authorId);

        deleted = true;
    }

    private void validateBelongToStudy(final Long associatedStudy) {
        if (!this.studyRoom.getId().equals(associatedStudy)) {
            throw new NotRelatedLinkException();
        }
    }

    private void validateAuthor(final Long authorId) {
        if (!this.authorId.equals(authorId)) {
            throw new NotLinkAuthorException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
