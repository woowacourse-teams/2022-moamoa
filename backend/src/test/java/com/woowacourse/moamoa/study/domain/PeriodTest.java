package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PeriodTest {

    @DisplayName("시작일자는 종료일자보다 클 수 없다.")
    @Test
    void startDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new Period(
                LocalDate.of(2022, 7, 10),
                LocalDate.of(2022, 7, 9)))
                .isInstanceOf(InvalidPeriodException.class);
    }

//    @DisplayName("모집 상태의 기간인지를 확인한다.")
//    @Test
//    public void checkParticipatingPeriod() {
//        final LocalDate enrollmentEndDate = LocalDate.now().plusDays(1);
//        final LocalDate startDate = LocalDate.now().plusDays(1);
//        final LocalDate endDate = LocalDate.now().plusDays(1);
//
//        final Period period = new Period(enrollmentEndDate, startDate, endDate);
//
//        assertThat(period.isCloseEnrollment()).isFalse();
//    }
}
