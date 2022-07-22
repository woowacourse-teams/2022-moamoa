package com.woowacourse.moamoa.study.domain;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Period {

    private LocalDateTime enrollmentEndDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Period() {
    }

    public Period(final LocalDateTime enrollmentEndDate, final LocalDateTime startDate, final LocalDateTime endDate) {
        if ((endDate != null && startDate.isAfter(endDate)) || (enrollmentEndDate != null && enrollmentEndDate.isAfter(endDate))) {
            throw new InvalidPeriodException();
        }
        this.enrollmentEndDate = enrollmentEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBefore(final LocalDateTime createAt) {
        return startDate.isBefore(createAt) || (enrollmentEndDate != null && enrollmentEndDate.isBefore(createAt));
    }

    public LocalDateTime getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Period period = (Period) o;
        return Objects.equals(enrollmentEndDate, period.enrollmentEndDate) && Objects.equals(startDate,
                period.startDate) && Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentEndDate, startDate, endDate);
    }
}
