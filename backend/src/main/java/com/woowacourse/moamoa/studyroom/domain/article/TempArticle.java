package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    private String title;

    @Lob
    private String content;

    private Long authorId;

    @Enumerated(EnumType.STRING)
    private ArticleType type;

    private TempArticle(final String title, final String content, final Long authorId, final StudyRoom studyRoom, final ArticleType type) {
        this(null, title, content, authorId, studyRoom, type);
    }

    private TempArticle(final Long id, final String title, final String content, final Long authorId,
                       final StudyRoom studyRoom, final ArticleType type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.studyRoom = studyRoom;
        this.type = type;
    }

    public static TempArticle create(
            final StudyRoom studyRoom, final Accessor accessor,
            final String title, final String content, final ArticleType type
    ) {
        if (type.isUnwritableAccessor(studyRoom, accessor)) {
            throw new UnwritableException(studyRoom.getId(), accessor, "TEMP_" + type.name());
        }

        return new TempArticle(title, content, accessor.getMemberId(), studyRoom, type);
    }

    public boolean isForbiddenAccessor(final Accessor accessor) {
        return type.isUneditableAccessor(studyRoom, authorId, accessor);
    }

    public void update(final Accessor accessor, final String title, final String content) {
        if (isForbiddenAccessor(accessor)) {
            throw UneditableException.forTempArticle(id, accessor);
        }

        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
