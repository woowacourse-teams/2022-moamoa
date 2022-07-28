package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.service.exception.InvalidParticipationStudyException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Details {

    private static final String CLOSE = "CLOSE";

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String excerpt;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String description;

    public Details(final String title, final String excerpt, final String thumbnail, final String status,
                   final String description) {
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.status = status;
        this.description = description;
    }

    protected void checkClosingRecruitment() {
        if (status.equals(CLOSE)) {
            throw new InvalidParticipationStudyException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Details details = (Details) o;
        return Objects.equals(title, details.title) && Objects.equals(excerpt, details.excerpt)
                && Objects.equals(thumbnail, details.thumbnail) && Objects.equals(status,
                details.status) && Objects.equals(description, details.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, excerpt, thumbnail, status, description);
    }
}
