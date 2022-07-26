package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudyTest {

    @DisplayName("생성일자는 스터디 시작일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeStartDate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSizeAndOwnerId(10, member.getId());

        assertThatThrownBy(() -> new Study(details, participants,
                new Period(LocalDate.now().plusDays(10), LocalDate.now().minusDays(1),
                        LocalDate.now().plusDays(20)), AttachedTags.empty()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("생성일자는 모집완료일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeEnrollmentEndDate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSizeAndOwnerId(10, member.getId());

        assertThatThrownBy(() -> new Study(details, participants,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(4),
                        LocalDate.now().plusDays(20)), AttachedTags.empty()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @Test
    @DisplayName("생성일자는 시작, 모집 종료, 종료 일자와 동일할 수 있다.")
    void createdAtCanSameWithStartAndEndAndEnrollmentDate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSizeAndOwnerId(10, member.getId());

        assertThatCode(() -> new Study(details, participants,
                new Period(LocalDate.now(), LocalDate.now(), LocalDate.now()), AttachedTags.empty()))
                .doesNotThrowAnyException();
    }

    @DisplayName("스터디 참여가 가능한 조건(날짜, 가입 가능 수, 가입여부)이면 스터디에 참여할 수 있다.")
    @Test
    public void participate() {
        final Details details = new Details("title", "excerpt", "thumbnail", "OPEN", "description");
        final Member member = new Member(1L, 1L, "username", "image", "profile");
        final Participants participants = Participants.createByMaxSizeAndOwnerId(10, member.getId());

        final LocalDate enrollmentEndDate = LocalDate.now().plusDays(1);
        final LocalDate startDate = LocalDate.now().plusDays(1);
        final LocalDate endDate = LocalDate.now().plusDays(1);

        final Period period = new Period(enrollmentEndDate, startDate, endDate);

        final Study study = new Study(details, participants, period, AttachedTags.empty());

        final Member participant = new Member(2L, 2L, "username", "image", "profile");

        assertThatCode(() -> study.participate(participant)).doesNotThrowAnyException();
    }
}
