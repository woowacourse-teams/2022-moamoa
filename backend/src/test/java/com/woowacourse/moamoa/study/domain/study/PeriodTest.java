package com.woowacourse.moamoa.study.domain.study;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PeriodTest {

    @DisplayName("시작일자는 종료일자보다 클 수 없다.")
    @Test
    void startDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new Period(LocalDateTime.of(2022, 7, 7, 0, 0),
                LocalDateTime.of(2022, 7, 10, 0, 0),
                LocalDateTime.of(2022, 7, 9, 0, 0)))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("모집완료는 종료일자보다 클 수 없다.")
    @Test
    void enrollmentDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new Period(LocalDateTime.of(2022, 7, 10, 0, 0),
                LocalDateTime.of(2022, 7, 8, 0, 0),
                LocalDateTime.of(2022, 7, 9, 0, 0)))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("모집완료는 시작일자와 연관된 조건이 없다.")
    @ParameterizedTest
    @MethodSource("provideEnrollmentEndDateAndStartDate")
    void enrollmentEndDateAndStartDateNotHasRelatedConditions(LocalDateTime enrollmentEndDate, LocalDateTime startDate) {
        assertThatNoException().isThrownBy(() -> new Period(enrollmentEndDate, startDate,
                LocalDateTime.of(2022, 7, 15, 0, 0)));
    }

    private static Stream<Arguments> provideEnrollmentEndDateAndStartDate() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2022, 7, 10, 0, 0), LocalDateTime.of(2022, 7, 9, 0, 0)),
                Arguments.of(LocalDateTime.of(2022, 7, 10, 0, 0), LocalDateTime.of(2022, 7, 10, 0, 0)),
                Arguments.of(LocalDateTime.of(2022, 7, 10, 0, 0), LocalDateTime.of(2022, 7, 11, 0, 0))
        );
    }
}
