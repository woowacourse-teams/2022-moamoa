package com.woowacourse.moamoa.study.domain;

import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
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
    private String recruitStatus;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private StudyStatus studyStatus;

    @Column(nullable = false)
    private String description;

    public Details(final String title, final String excerpt, final String thumbnail, final String recruitStatus,
                   final StudyStatus studyStatus, final String description) {
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.recruitStatus = recruitStatus;
        this.studyStatus = studyStatus;
        this.description = description;
    }

    public boolean isCloseStatus() {
        return recruitStatus.equals(CLOSE);
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
