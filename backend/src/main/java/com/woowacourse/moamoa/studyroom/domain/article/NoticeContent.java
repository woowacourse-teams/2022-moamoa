package com.woowacourse.moamoa.studyroom.domain.article;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeContent implements Content<NoticeArticle> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public NoticeContent(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public NoticeArticle createArticle(final StudyRoom studyRoom, final Accessor accessor) {
        if (studyRoom.isOwner(accessor)) {
            return new NoticeArticle(accessor.getMemberId(), studyRoom, this);
        }

        throw new UneditableArticleException(studyRoom.getId(), accessor, ArticleType.NOTICE);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoticeContent that = (NoticeContent) o;
        return Objects.equals(title, that.title) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
