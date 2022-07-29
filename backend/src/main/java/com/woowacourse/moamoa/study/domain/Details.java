package com.woowacourse.moamoa.study.domain;

import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Details {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String excerpt;

    @Column(nullable = false)
    private String thumbnail;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private StudyStatus studyStatus;

    @Column(nullable = false)
    private String description;

    public Details(final String title, final String excerpt, final String thumbnail,
                   final StudyStatus studyStatus, final String description) {
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.studyStatus = studyStatus;
        this.description = description;
    }

    boolean isPreparingStudy() {
        return studyStatus.equals(StudyStatus.PREPARE);
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

    public StudyStatus getStudyStatus() {
        return studyStatus;
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
        final Details details = (Details) o;
        return Objects.equals(title, details.title) && Objects.equals(excerpt, details.excerpt)
                && Objects.equals(thumbnail, details.thumbnail) && Objects.equals(studyStatus, details.studyStatus)
                && Objects.equals(description, details.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, excerpt, thumbnail, studyStatus, description);
    }
}
