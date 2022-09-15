package com.woowacourse.moamoa.studyroom.domain.article;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Objects;
import javax.persistence.Column;
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
public class CommunityArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    private String title;

    private String content;

    private boolean deleted;

    public CommunityArticle(final String title, final String content, final Long authorId,
                     final StudyRoom studyRoom) {
        this(null, title, content, authorId, studyRoom);
    }

    public CommunityArticle(final Long id, final String title, final String content, final Long authorId,
                            final StudyRoom studyRoom) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
        this.title = title;
        this.content = content;
        this.deleted = false;
    }

    public void update(final Accessor accessor, final String title, final String content) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.COMMUNITY);
        }

        this.title = title;
        this.content = content;
    }

    private boolean isEditableAccessor(final Accessor accessor) {
        return studyRoom.isPermittedAccessor(accessor) && authorId.equals(accessor.getMemberId());
    }

    public void delete(final Accessor accessor) {
        if (!isEditableAccessor(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.COMMUNITY);
        }

        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
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
