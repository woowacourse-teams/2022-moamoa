package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.*;
import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
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
        final LocalDateTime now = now();

        final LocalDateTime createdAt = now;
        final LocalDate startDate = now.toLocalDate().minusDays(1);
        final LocalDate enrollmentEndDate = now.toLocalDate().plusDays(10);
        final LocalDate endDate = now.toLocalDate().plusDays(20);

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, enrollmentEndDate);
        final StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        assertThatThrownBy(() ->
                new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt)
        ).isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("생성일자는 모집완료일자보다 클 수 없다.")
    @Test
    void createdAtMustBeforeEnrollmentEndDate() {
        final LocalDateTime now = now();

        final LocalDateTime createdAt = now;
        final LocalDate enrollmentEndDate = now.toLocalDate().minusDays(1);
        final LocalDate startDate = now.toLocalDate().plusDays(4);
        final LocalDate endDate = now.toLocalDate().plusDays(20);

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, enrollmentEndDate);
        final StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, PREPARE);

        assertThatThrownBy(() ->
                new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt)
        ).isInstanceOf(InvalidPeriodException.class);
    }

    @Test
    @DisplayName("생성일자는 시작, 모집 종료, 종료 일자와 동일할 수 있다.")
    void createdAtCanSameWithStartAndEndAndEnrollmentDate() {
        final LocalDateTime now = now();

        final LocalDateTime createdAt = now;
        final LocalDate enrollmentEndDate = now.toLocalDate();
        final LocalDate startDate = now.toLocalDate();
        final LocalDate endDate = now.toLocalDate();

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, enrollmentEndDate);
        final StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        assertThatCode(() ->
                new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt)
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("모집 기간은 스터디 종료 일자보다 전이여야 된다.")
    void enrollmentEndDateIsBeforeEndDate() {
        final LocalDateTime now = now();

        final LocalDateTime createdAt = now;
        final LocalDate enrollmentEndDate = now.toLocalDate().plusDays(1);
        final LocalDate startDate = now.toLocalDate();
        final LocalDate endDate = now.toLocalDate();

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, enrollmentEndDate);
        final StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        assertThatCode(() ->
                new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt)
        ).isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("새로운 사용자는 스터디에 가입할 수 있다.")
    @Test
    public void participate() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        sut.participate(2L);

        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
        assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
    }

    @DisplayName("기존 참여자는 스터디에 가입할 수 없다.")
    @Test
    public void participateTwice() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        sut.participate(2L);

        assertThatThrownBy(() -> sut.participate(2L)).isInstanceOf(FailureParticipationException.class);
        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));

    }

    @DisplayName("방장은 스터디에 가입할 수 없다.")
    @Test
    void participateByOwner() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        assertThatThrownBy(() -> sut.participate(1L)).isInstanceOf(FailureParticipationException.class);
        assertThat(sut.getParticipants()).isEqualTo(Participants.createBy(1L));
    }

    @DisplayName("인원이 가득찬 스터디는 가입할 수 없다.")
    @Test
    void participateFullOfMemberStudy() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        sut.participate(2L);

        assertThatThrownBy(() -> sut.participate(3L)).isInstanceOf(FailureParticipationException.class);
        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
    }

    @DisplayName("마지막 인원이 참여시 스터디 모집은 종료된다.")
    @Test
    public void closeStudyByLastParticipant() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        sut.participate(2L);

        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
        assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("리뷰는 스터디 시작 후 작성할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideStudyPeriod")
    void writeReviewAfterStartDate(
            final LocalDateTime createdAt, final LocalDate enrollmentEndDate,
            final StudyPlanner studyPlanner, boolean isWritable
    ) {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(10, RECRUITMENT_START, enrollmentEndDate);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(),
                createdAt);

        assertThat(sut.isWritableReviews(1L)).isEqualTo(isWritable);
    }

    private static Stream<Arguments> provideStudyPeriod() {
        final LocalDateTime now = now();

        return Stream.of(
                Arguments.of(
                        now,
                        now.toLocalDate(),
                        new StudyPlanner(now.toLocalDate().plusDays(1), now.toLocalDate().plusDays(10), PREPARE),
                        false
                ),
                Arguments.of(
                        now.minusDays(1),
                        now.toLocalDate(),
                        new StudyPlanner(now.toLocalDate(), now.toLocalDate().plusDays(10), IN_PROGRESS),
                        true
                ),
                Arguments.of(
                        now.minusDays(3),
                        now.toLocalDate().minusDays(2),
                        new StudyPlanner(now.toLocalDate().minusDays(2), now.toLocalDate().minusDays(1), DONE),
                        true
                )
        );
    }

    @DisplayName("스터디에 참여한 방장은 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByOwner() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        assertThat(sut.isWritableReviews(1L)).isTrue();
    }

    @DisplayName("스터디에 참여한 사용자는 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByParticipant() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        sut.participate(2L);

        assertThat(sut.isWritableReviews(2L)).isTrue();
    }

    @DisplayName("스터디에 참여하지 않은 사용자는 리뷰를 작성할 수 없다.")
    @Test
    void writeReviewByNotParticipant() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, LocalDate.now());
        final StudyPlanner studyPlanner = new StudyPlanner(LocalDate.now(), LocalDate.now(), IN_PROGRESS);
        final Study sut = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now());

        assertThat(sut.isWritableReviews(2L)).isFalse();
    }
}
