package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class StudyPlanner {

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private StudyStatus studyStatus;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    public StudyPlanner(final LocalDate startDate, final LocalDate endDate, final StudyStatus studyStatus) {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidPeriodException();
        }
        this.studyStatus = studyStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    boolean isStartBeforeThan(LocalDate date) {
        return startDate.isBefore(date);
    }

    boolean isEndBeforeThan(LocalDate date) {
        if (endDate == null) {
            return false;
        }
        return endDate.isBefore(date);
    }

    void updateStatus(final LocalDate now) {
        if (isNeedToCloseStudy(now)) {
            studyStatus = DONE;
        }
        if (isNeedToChangeProgress(now)) {
            studyStatus = IN_PROGRESS;
        }
    }

    private boolean isNeedToCloseStudy(final LocalDate now) {
        return (endDate != null) && (studyStatus.equals(IN_PROGRESS)) && (endDate.isAfter(now) || endDate.isEqual(now));
    }

    private boolean isNeedToChangeProgress(final LocalDate now) {
        return (studyStatus.equals(PREPARE)) && (startDate.isAfter(now) || startDate.isEqual(now));
    }

    boolean isProgress() {
        return studyStatus.equals(IN_PROGRESS);
    }

    boolean isPreparing() {
        return studyStatus.equals(PREPARE);
    }

    boolean isCloseStudy() {
        return studyStatus.equals(DONE);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudyPlanner studyPlanner = (StudyPlanner) o;
        return Objects.equals(startDate, studyPlanner.startDate) && Objects.equals(endDate, studyPlanner.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
