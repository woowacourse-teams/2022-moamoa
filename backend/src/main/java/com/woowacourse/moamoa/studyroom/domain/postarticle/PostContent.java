package com.woowacourse.moamoa.studyroom.domain.postarticle;

import com.woowacourse.moamoa.studyroom.domain.Content;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
public class PostContent implements Content<PostContent> {

    private String title;

    private String content;

    public PostContent(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void update(PostContent content) {
        this.title = content.title;
        this.content = content.content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostContent that = (PostContent) o;
        return Objects.equals(title, that.title) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
