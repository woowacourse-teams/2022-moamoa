package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudyTest {

    @DisplayName("생성일자는 스터디 시작일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeStartDate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSize(10, member.getId());

        assertThatThrownBy(() -> new Study(details, participants,
                new Period(LocalDateTime.now().plusDays(10), LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(20)), AttachedTags.empty()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("생성일자는 모집완료일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeEnrollmentEndDate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSize(10, member.getId());

        assertThatThrownBy(() -> new Study(details, participants,
                new Period(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(4),
                        LocalDateTime.now().plusDays(20)), AttachedTags.empty()))
                .isInstanceOf(InvalidPeriodException.class);
    }
}
