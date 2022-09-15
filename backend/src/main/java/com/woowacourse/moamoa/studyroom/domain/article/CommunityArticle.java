package com.woowacourse.moamoa.studyroom.domain.article;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "community")
@Where(clause = "deleted = false")
public class CommunityArticle extends BaseEntity implements Article<CommunityContent> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    @Embedded
    private CommunityContent content;

    private boolean deleted;

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
        this.deleted = false;
    }

    @Override
    public void update(final Accessor accessor, final CommunityContent content) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.COMMUNITY);
        }

        this.content = content;
    }

    private boolean isEditableAccessor(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor) && authorId.equals(accessor.getMemberId());
    }

    @Override
    public void delete(final Accessor accessor) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.COMMUNITY);
        }

        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
