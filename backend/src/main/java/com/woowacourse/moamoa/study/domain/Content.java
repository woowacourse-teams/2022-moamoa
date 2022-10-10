package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Content {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String excerpt;

    @Column(nullable = false)
    private String thumbnail;

    @Lob
    @Column(nullable = false)
    private String description;

    public Content(final String title, final String excerpt, final String thumbnail, final String description) {
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Content content = (Content) o;
        return Objects.equals(title, content.title) && Objects.equals(excerpt, content.excerpt)
                && Objects.equals(thumbnail, content.thumbnail) && Objects.equals(description, content.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, excerpt, thumbnail, description);
    }
}
