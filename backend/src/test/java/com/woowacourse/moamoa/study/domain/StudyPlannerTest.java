package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyPlannerTest {

    @DisplayName("시작일자는 종료일자보다 클 수 없다.")
    @Test
    void startDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new StudyPlanner(
                LocalDate.of(2022, 7, 10),
                LocalDate.of(2022, 7, 9), StudyStatus.PREPARE))
                .isInstanceOf(InvalidPeriodException.class);
    }
}
