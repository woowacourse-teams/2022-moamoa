package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PeriodTest {

    private static final LocalDate BASE_DATE = LocalDate.now().plusDays(5);
    private static final LocalDate END_DATE = LocalDate.now().plusDays(6);

    @DisplayName("시작일자는 종료일자보다 클 수 없다.")
    @Test
    void startDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new Period(LocalDate.of(2022, 7, 7),
                LocalDate.of(2022, 7, 10),
                LocalDate.of(2022, 7, 9)))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("모집완료는 종료일자보다 클 수 없다.")
    @Test
    void enrollmentDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new Period(LocalDate.of(2022, 7, 10),
                LocalDate.of(2022, 7, 8),
                LocalDate.of(2022, 7, 9)))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("모집완료는 시작일자와 연관된 조건이 없다.")
    @ParameterizedTest
    @MethodSource("provideEnrollmentEndDateAndStartDate")
    void enrollmentEndDateAndStartDateNotHasRelatedConditions(LocalDate enrollmentEndDate, LocalDate startDate) {
        assertThatNoException().isThrownBy(() -> new Period(enrollmentEndDate, startDate, END_DATE));
    }

    private static Stream<Arguments> provideEnrollmentEndDateAndStartDate() {
        return Stream.of(
                Arguments.of(BASE_DATE, BASE_DATE.minusDays(1)),
                Arguments.of(BASE_DATE, BASE_DATE),
                Arguments.of(BASE_DATE, BASE_DATE.plusDays(1))
        );
    }

    @DisplayName("모집 상태의 기간인지를 확인한다.")
    @Test
    public void checkParticipatingPeriod() {
        final LocalDate enrollmentEndDate = LocalDate.now().plusDays(1);
        final LocalDate startDate = LocalDate.now().plusDays(1);
        final LocalDate endDate = LocalDate.now().plusDays(1);

        final Period period = new Period(enrollmentEndDate, startDate, endDate);

        assertThat(period.isCloseEnrollment()).isFalse();
    }
}
