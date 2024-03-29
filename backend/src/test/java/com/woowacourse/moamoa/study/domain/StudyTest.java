package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.study.domain.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import com.woowacourse.moamoa.study.service.exception.OwnerCanNotLeaveException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StudyTest {

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

        assertThatThrownBy(() ->
                new Study(content, participants, AttachedTags.empty(), createdAt, 10,
                        enrollmentEndDate, startDate, endDate)
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

        assertThatThrownBy(() ->
                new Study(content, participants, AttachedTags.empty(), createdAt, 10,
                        enrollmentEndDate, startDate, endDate)
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

        assertThatCode(() ->
                new Study(content, participants, AttachedTags.empty(), createdAt, 10,
                        enrollmentEndDate, startDate, endDate)
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

        assertThatCode(() ->
                new Study(content, participants, AttachedTags.empty(), createdAt, 10,
                        enrollmentEndDate, startDate, endDate)
        ).isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("새로운 사용자는 스터디에 가입할 수 있다.")
    @Test
    void participate() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        sut.participate(2L);

        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
        assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
    }

    @DisplayName("기존 참여자는 스터디에 가입할 수 없다.")
    @Test
    void participateTwice() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        sut.participate(2L);

        assertThatThrownBy(() -> sut.participate(2L)).isInstanceOf(FailureParticipationException.class);
        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));

    }

    @DisplayName("방장은 스터디에 가입할 수 없다.")
    @Test
    void participateByOwner() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

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
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now());
        sut.participate(2L);

        assertThatThrownBy(() -> sut.participate(3L)).isInstanceOf(FailureParticipationException.class);
        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
    }

    @DisplayName("마지막 인원이 참여시 스터디 모집은 종료된다.")
    @Test
    void closeStudyByLastParticipant() {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        sut.participate(2L);

        assertThat(sut.getParticipants()).isEqualTo(new Participants(1L, Set.of(2L)));
        assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("스터디에서 나의 역할을 조회한다.")
    @ParameterizedTest
    @CsvSource(value = {"2,MEMBER", "3,NON_MEMBER", "1,OWNER"})
    void getMyRoleInStudy(Long memberId, MemberRole role) {
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        sut.participate(2L);

        assertThat(sut.getRole(memberId)).isEqualTo(role);
    }

    @DisplayName("스터디 종료기간이 넘으면 자동으로 종료 상태가 된다.")
    @Test
    void autoCloseStudyStatus() {
        // given
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(3));

        // when
        sut.changeStatus(LocalDate.now().plusDays(4));

        // then
        assertThat(sut.isCloseStudy()).isTrue();
    }

    @DisplayName("스터디 시작기간(StartDate)이 되면 자동으로 진행중 상태가 된다.")
    @Test
    void updateInProgressStatus() {
        // given
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));

        // when
        sut.changeStatus(LocalDate.now());

        // then
        assertThat(sut.isProgressStatus()).isTrue();
    }

    @DisplayName("모집 기간이 지난 스터디는 자동으로 모집이 종료된다.")
    @Test
    void autoCloseEnrollment() {
        // given
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now().minusDays(1), 2,
                LocalDate.now().minusDays(1), LocalDate.now(), LocalDate.now().plusDays(5));

        // when
        sut.changeStatus(LocalDate.now());

        // then
        assertThat(sut.getRecruitPlanner().isCloseEnrollment()).isTrue();
    }

    @DisplayName("참여자는 방장, 참가자만 가능하다.")
    @ParameterizedTest
    @CsvSource({"1, 2, 1, true", "1, 2, 2, true", "1, 2, 3, false"})
    void checkIsParticipant(Long ownerId, Long participantId, Long targetMemberId, boolean expected) {
        // arrange
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(ownerId);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));

        sut.participate(participantId);

        // act
        assertThat(sut.isParticipant(targetMemberId)).isEqualTo(expected);
    }

    @DisplayName("스터디장은 탈퇴할 수 없다.")
    @Test
    void notLeaveOwner() {
        final Participant owner = new Participant(1L);

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(owner.getMemberId());
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));

        assertThatThrownBy(() -> sut.leave(owner, new DateTimeSystem().now().toLocalDate()))
                .isInstanceOf(OwnerCanNotLeaveException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 탈퇴할 수 없다.")
    @Test
    void notLeaveNonParticipatedMember() {
        final Participant owner = new Participant(1L);
        final Participant participant = new Participant(2L);

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(owner.getMemberId());
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));

        assertThatThrownBy(() -> sut.leave(participant, new DateTimeSystem().now().toLocalDate()))
                .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("스터디원은 탈퇴할 수 있다.")
    @Test
    void LeaveParticipatedMember() {
        final Participant owner = new Participant(1L);
        final Participant participant = new Participant(2L);

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(owner.getMemberId());
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));

        sut.participate(participant.getMemberId());

        assertAll(
                () -> assertDoesNotThrow(() -> sut.leave(participant, new DateTimeSystem().now().toLocalDate())),
                () -> assertThat(sut.getParticipants()).isEqualTo(new Participants(owner.getMemberId(), Set.of()))
        );
    }

    @DisplayName("참여자는 스터디를 업데이트할 수 없다.")
    @Test
    void updateStudyWithParticipant() {
        // given
        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = Participants.createBy(1L);
        final Study sut = new Study(content, participants, AttachedTags.empty(), now(), 2,
                LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(5));
        final Content updatingContent = new Content("새로운 title", "새로운 excerpt", "새로운 thumbnail", "새로운 description");

        // when & then
        assertThatThrownBy(() -> sut.updateContent(2L, updatingContent, null))
                .isInstanceOf(UnauthorizedException.class);
    }
}
