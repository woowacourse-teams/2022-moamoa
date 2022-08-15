package com.woowacourse.moamoa.studyroom.domain.postarticle;

import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE notice SET deleted = true WHERE id = ?")
@Getter
@Table(name = "notice")
public class NoticeArticle extends PostArticle {

    private boolean deleted;

    public NoticeArticle(PostContent content, final Long authorId, final StudyRoom studyRoom) {
        super(null, authorId, content, studyRoom);
        this.deleted = false;
    }

    public NoticeArticle(final Long id, final String title, final String content, final Long authorId,
                         final StudyRoom studyRoom) {
        super(id, authorId, new PostContent(title, content), studyRoom);
        this.deleted = false;
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
        return Objects.equals(getId(), that.getId()) && Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getAuthorId(), that.getAuthorId()) &&
                Objects.equals(getStudyRoom(), that.getStudyRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getAuthorId(), getStudyRoom());
    }
}
