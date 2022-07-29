package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StudyTest {

    @DisplayName("생성일자는 스터디 시작일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeStartDate() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(10, 1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, LocalDate.now().plusDays(10));

        assertThatThrownBy(() -> new Study(content, participants, recruitPlanner,
                new StudyPlanner(LocalDate.now().minusDays(1), LocalDate.now().plusDays(20), PREPARE),
                AttachedTags.empty(), LocalDateTime.now()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("생성일자는 모집완료일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeEnrollmentEndDate() {
        final Content content = new Content("title", "excerpt", "thumbnail",
                "description");
        final Participants participants = Participants.createBy(10, 1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, LocalDate.now().minusDays(1));

        assertThatThrownBy(() -> new Study(content, participants,
                recruitPlanner, new StudyPlanner(LocalDate.now().plusDays(4), LocalDate.now().plusDays(20), PREPARE),
                AttachedTags.empty(), LocalDateTime.now()
        ))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @Test
    @DisplayName("생성일자는 시작, 모집 종료, 종료 일자와 동일할 수 있다.")
    void createdAtCanSameWithStartAndEndAndEnrollmentDate() {
        final Content content = new Content("title", "excerpt", "thumbnail",
                "description");
        final Participants participants = Participants.createBy(10, 1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, LocalDate.now());

        assertThatCode(() -> new Study(content, participants,
                recruitPlanner, new StudyPlanner(LocalDate.now(), LocalDate.now(), PREPARE), AttachedTags.empty(),
                LocalDateTime.now()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("모집 기간은 스터디 종료 일자보다 전이여야 된다.")
    void enrollmentEndDateIsBeforeEndDate() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(10,1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, LocalDate.now().plusDays(1));

        assertThatCode(() -> new Study(content, participants,
                recruitPlanner, new StudyPlanner(LocalDate.now(), LocalDate.now(), PREPARE), AttachedTags.empty(),
                LocalDateTime.now()))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("스터디 참여가 가능한 조건(날짜, 가입 가능 수, 가입여부)이면 스터디에 참여할 수 있다.")
    @Test
    public void participate() {
        final Content content = new Content("title", "excerpt", "thumbnail",
                "description");
        final Member member = new Member(1L, 1L, "username", "image", "profile");
        final LocalDate enrollmentEndDate = LocalDate.now().plusDays(1);
        final Participants participants = Participants.createBy(10, member.getId());
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, enrollmentEndDate);
        final LocalDate startDate = LocalDate.now().plusDays(1);
        final LocalDate endDate = LocalDate.now().plusDays(1);

        final StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, PREPARE);

        final Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), LocalDateTime.now()
        );

        final Member participant = new Member(2L, 2L, "username", "image", "profile");

        assertThatCode(() -> study.participate(participant.getId())).doesNotThrowAnyException();
    }

    @DisplayName("리뷰는 스터디 시작 후 작성할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideStudyPeriod")
    void writeReviewAfterStartDate(StudyStatus studyStatus, boolean isWritable) {
        final Content content = new Content("title", "excerpt", "thumbnail",
                "description");
        final Participants participants = Participants.createBy(10, 1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(RecruitStatus.OPEN, LocalDate.now());

        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now().plusDays(3), studyStatus);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), LocalDateTime.now()
        );

        assertThat(sut.isWritableReviews(1L)).isEqualTo(isWritable);
    }

    private static Stream<Arguments> provideStudyPeriod() {
        return Stream.of(
                Arguments.of(PREPARE, false),
                Arguments.of(IN_PROGRESS, true),
                Arguments.of(DONE, true)
        );
    }
}
