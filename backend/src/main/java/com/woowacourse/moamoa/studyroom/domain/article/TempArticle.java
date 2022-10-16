package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private StudyRoom studyRoom;

    @Embedded
    private Content content;

    private Long authorId;

    @Enumerated(EnumType.STRING)
    private ArticleType type;

    private TempArticle(
            final Content content, final Long authorId, final StudyRoom studyRoom, final ArticleType type
    ) {
        this(null, authorId, content, studyRoom, type);
    }

    private TempArticle(
            final Long id, final Long authorId, final Content content, final StudyRoom studyRoom, final ArticleType type
    ) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.studyRoom = studyRoom;
        this.type = type;
    }

    public static TempArticle create(
            final Content content, final StudyRoom studyRoom, final Accessor accessor, final ArticleType type
    ) {
        if (type.isUnwritableAccessor(studyRoom, accessor)) {
            throw new UnwritableException(studyRoom.getId(), accessor, "TEMP_" + type.name());
        }

        return new TempArticle(content, accessor.getMemberId(), studyRoom, type);
    }

    public boolean isForbiddenAccessor(final Accessor accessor) {
        return type.isUneditableAccessor(studyRoom, authorId, accessor);
    }

    public void update(final Accessor accessor, Content content) {
        if (isForbiddenAccessor(accessor)) {
            throw UneditableException.forTempArticle(id, accessor);
        }

        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return content.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }
}
