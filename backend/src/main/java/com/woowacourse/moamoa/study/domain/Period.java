package com.woowacourse.moamoa.study.domain;

import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Period {

    private LocalDate enrollmentEndDate;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    public Period(final LocalDate enrollmentEndDate, final LocalDate startDate, final LocalDate endDate) {
        validatePeriod(enrollmentEndDate, startDate, endDate);
        this.enrollmentEndDate = enrollmentEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validatePeriod(final LocalDate enrollmentEndDate, final LocalDate startDate, final LocalDate endDate) {
        if (isImproperStudyDate(startDate, endDate) || isImproperEnrollmentEndDate(enrollmentEndDate, endDate)) {
            throw new InvalidPeriodException();
        }
    }

    private boolean isImproperStudyDate(final LocalDate startDate, final LocalDate endDate) {
        return endDate != null && startDate.isAfter(endDate);
    }

    private boolean isImproperEnrollmentEndDate(final LocalDate enrollmentEndDate, final LocalDate endDate) {
        return enrollmentEndDate != null && endDate != null && enrollmentEndDate.isAfter(endDate);
    }

    boolean isBefore(final LocalDateTime createAt) {
        return startDate.isBefore(createAt.toLocalDate()) || (enrollmentEndDate != null && enrollmentEndDate.isBefore(
                createAt.toLocalDate()));
    }

    boolean isCloseEnrollment() {
        return enrollmentEndDate != null && enrollmentEndDate.isBefore(LocalDate.now());
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
