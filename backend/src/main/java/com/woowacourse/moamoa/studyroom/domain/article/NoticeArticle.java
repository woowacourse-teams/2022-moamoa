package com.woowacourse.moamoa.studyroom.domain.article;

import static javax.persistence.GenerationType.IDENTITY;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Objects;
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
@Table(name = "notice")
@Where(clause = "deleted = false")
public class NoticeArticle extends BaseEntity implements Article<NoticeContent> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    @Embedded
    private NoticeContent content;

    private boolean deleted;

    NoticeArticle(final Long authorId, final StudyRoom studyRoom, final NoticeContent content) {
        this(null, authorId, studyRoom, content);
    }

    private NoticeArticle(final Long id, final Long authorId,
                         final StudyRoom studyRoom, final NoticeContent content) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
        this.deleted = false;
        this.content = content;
    }

    @Override
    public void update(final Accessor accessor, final NoticeContent content) {
        if (!studyRoom.isOwner(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.NOTICE);
        }

        this.content = content;
    }

    @Override
    public void delete(final Accessor accessor) {
        if (!studyRoom.isOwner(accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.NOTICE);
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
        final NoticeArticle that = (NoticeArticle) o;
        return deleted == that.deleted && Objects.equals(id, that.id) && Objects
                .equals(authorId, that.authorId) && Objects.equals(studyRoom, that.studyRoom) && Objects
                .equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId, studyRoom, content, deleted);
    }
}
