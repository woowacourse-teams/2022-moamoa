package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.service.exception.InvalidParticipationStudyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DetailsTest {

    private static final String CLOSE = "CLOSE";

    @DisplayName("Study의 상태가 CLOSE 이면 모집 기간이 아니므로 예외가 발생한다.")
    @Test
    public void checkStudyStatus() {
        final Details details = new Details("title", "excerpt", "thumbnail", CLOSE, "description");

        assertThatThrownBy(details::checkStudyStatus)
                .isInstanceOf(InvalidParticipationStudyException.class);
    }
}
