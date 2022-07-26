package com.woowacourse.moamoa.study.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Details {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String excerpt;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String recruitStatus;

    @Column(nullable = false)
    private String studyStatus;

    @Column(nullable = false)
    private String description;

    protected Details() {
    }

    public Details(final String title, final String excerpt, final String thumbnail, final String recruit_status,
                   final String study_status,
                   final String description) {
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.recruitStatus = recruit_status;
        this.studyStatus = study_status;
        this.description = description;
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
                && Objects.equals(thumbnail, details.thumbnail) && Objects.equals(recruitStatus,
                details.recruitStatus) && Objects.equals(studyStatus, details.studyStatus)
                && Objects.equals(description, details.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, excerpt, thumbnail, recruitStatus, studyStatus, description);
    }
}
