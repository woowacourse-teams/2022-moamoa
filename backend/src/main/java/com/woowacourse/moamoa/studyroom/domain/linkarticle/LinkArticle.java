package com.woowacourse.moamoa.studyroom.domain.linkarticle;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.Content;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.postarticle.PostContent;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "link")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE link SET deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LinkArticle extends Article {

    @Embedded
    private LinkContent content;

    private boolean deleted;

    public LinkArticle(final LinkContent content, final Long authorId, final StudyRoom studyRoom) {
        super(null, authorId, studyRoom);
        this.content = content;
        this.deleted = false;
    }

    @Override
    public void update(final Accessor accessor, final Content<?> content) {
        if (!isEditableBy(accessor) && !content.getClass().equals(LinkContent.class)) {
            throw new IllegalArgumentException();
        }
        this.content.update((LinkContent) content);
    }
}
