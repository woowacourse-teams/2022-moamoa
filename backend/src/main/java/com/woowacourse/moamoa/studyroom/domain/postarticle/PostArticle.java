package com.woowacourse.moamoa.studyroom.domain.postarticle;

import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.Content;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public abstract class PostArticle extends Article {

    @Embedded
    private PostContent content;

    public PostArticle(final Long id, final Long authorId, final PostContent postContent,
                         final StudyRoom studyRoom) {
        super(id, authorId, studyRoom);
        this.content = postContent;
    }

    @Override
    public void update(final Accessor accessor, final Content content) {
        if (!isEditableBy(accessor) && !content.getClass().equals(PostContent.class)) {
            throw new IllegalArgumentException();
        }
        this.content = (PostContent) content;
    }

    protected PostContent getContent() {
        return content;
    }
}
