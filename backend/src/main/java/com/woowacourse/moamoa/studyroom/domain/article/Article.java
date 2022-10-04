package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "article")
@Where(clause = "deleted = false")
public class Article extends BaseEntity {

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
    private Content content;

    @Enumerated(EnumType.STRING)
    private ArticleType type;

    public static Article create(
            final StudyRoom studyRoom, final Accessor accessor, final Content content, final ArticleType type
    ) {
        if (type.isUnwritableAccessor(studyRoom, accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, Article.class);
        }

        return new Article(null, studyRoom, accessor.getMemberId(), content, type);
    }

    private Article(
            final Long id, final StudyRoom studyRoom, final Long authorId, final Content content, final ArticleType type
    ) {
        this.id = id;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
        this.content = content;
        this.type = type;
    }

    public void update(final Accessor accessor, final Content content) {
        if (type.isUneditableAccessor(studyRoom, authorId, accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        this.content = content;
    }

    public final void delete(final Accessor accessor) {
        if (type.isUneditableAccessor(studyRoom, authorId, accessor)) {
            throw new UneditableArticleException(studyRoom.getId(), accessor, getClass());
        }

        deleted = true;
    }

    public Long getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }

    boolean isDeleted() {
        return deleted;
    }
}
