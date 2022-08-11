package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class StudyPlanner {

    @Enumerated(STRING)
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
        if (isNeedToChangeStatus(now)) {
            studyStatus = studyStatus.nextStatus();
        }
    }

    private boolean isNeedToChangeStatus(final LocalDate now) {
        return isNeedToCloseStudy(now) || isNeedToChangeProgress(now);
    }

    private boolean isNeedToCloseStudy(final LocalDate now) {
        return (endDate != null) && (isProgress()) && (endDate.isBefore(now) || endDate.isEqual(now));
    }

    private boolean isNeedToChangeProgress(final LocalDate now) {
        return (isPreparing()) && (startDate.isBefore(now) || startDate.isEqual(now));
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

    public boolean isInappropriateCondition(final LocalDate createdDate) {
        if (startDate.isEqual(createdDate) && studyStatus == IN_PROGRESS) {
            return false;
        }
        if ((startDate.isAfter(createdDate)) && (studyStatus == PREPARE || studyStatus == IN_PROGRESS)) {
            return false;
        }
        return !studyStatus.equals(DONE);
    }
}
