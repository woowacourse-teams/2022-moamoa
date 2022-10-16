package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.common.entity.BaseEntity;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long authorId;

    private Long studyId;

    private TempArticle(final String title, final String content, final Long authorId, final Long studyId) {
        this(null, title, content, authorId, studyId);
    }

    private TempArticle(final Long id, final String title, final String content, final Long authorId,
                       final Long studyId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.studyId = studyId;
    }

    public static TempArticle create(
            final StudyRoom studyRoom, final Accessor accessor, final String title, final String content,
            final ArticleType type
    ) {
        if (type.isUnwritableAccessor(studyRoom, accessor)) {
            throw new UnwritableException(studyRoom.getId(), accessor, "Temp Notice Article");
        }

        return new TempArticle(title, content, accessor.getMemberId(), studyRoom.getId());
    }

    public boolean isForbiddenAccessor(final Accessor accessor) {
        return !authorId.equals(accessor.getMemberId()) || !studyId.equals(accessor.getStudyId());
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }
}
