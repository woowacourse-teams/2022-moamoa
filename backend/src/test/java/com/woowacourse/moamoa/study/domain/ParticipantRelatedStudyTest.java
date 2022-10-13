package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.study.domain.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.service.exception.FailureKickOutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParticipantRelatedStudyTest {

    @DisplayName("방장이 모집 종료 이전에 스터디원을 강퇴시키는 경우 모집 상태는 RECRUITMENT_START다.")
    @Test
    void canKickOutStudyParticipantBeforeTheEnrollmentEndDate() {
        final long ownerId = 1L;
        final long participantId = 2L;

        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime createdAt = now;
        final LocalDate enrollmentEndDate = now.toLocalDate().plusDays(1);
        final LocalDate startDate = now.toLocalDate().plusDays(1);
        final LocalDate endDate = null;

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = new Participants(ownerId, Set.of(participantId));
        final Study sut = new Study(content, participants, AttachedTags.empty(), createdAt, 2,
                enrollmentEndDate, startDate, endDate);

        sut.kickOut(ownerId, new Participant(participantId), LocalDate.now());

        assertAll(
                () -> assertThat(sut.isParticipant(participantId)).isFalse(),
                () -> assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START)
        );
    }

    @DisplayName("방장이 모집 종료 이후에 스터디원을 강퇴시키는 경우 모집 상태는 RECRUITMENT_END이다.")
    @Test
    void canKickOutStudyParticipantAfterTheEnrollmentEndDate() {
        final long ownerId = 1L;
        final long participantId = 2L;

        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime createdAt = now.minusDays(2);
        final LocalDate enrollmentEndDate = now.toLocalDate().minusDays(1);
        final LocalDate startDate = now.toLocalDate();
        final LocalDate endDate = null;

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = new Participants(ownerId, Set.of(participantId));
        final Study sut = new Study(content, participants, AttachedTags.empty(), createdAt, 2,
                enrollmentEndDate, startDate, endDate);

        sut.kickOut(ownerId, new Participant(participantId), LocalDate.now());

        assertAll(
                () -> assertThat(sut.isParticipant(participantId)).isFalse(),
                () -> assertThat(sut.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END)
        );
    }

    @DisplayName("스터디원은 다른 스터디원을 강퇴시킬 수 없다.")
    @Test
    void canNotKickOutStudyParticipant() {
        // given
        final long ownerId = 1L;
        final long participantId1 = 2L;
        final long participantId2 = 3L;

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = new Participants(ownerId, Set.of(participantId1, participantId2));
        final Study sut = new Study(content, participants, AttachedTags.empty(), LocalDateTime.now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        assertThatThrownBy(() -> sut.kickOut(participantId1, new Participant(participantId2), LocalDate.now()))
                .isInstanceOf(FailureKickOutException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원을 강퇴시킬 수 없다.")
    @Test
    void canNotKickOutNotParticipatedMember() {
        // given
        final long ownerId = 1L;
        final long wrongParticipantId = 3L;

        final Content content = new Content("title", "excerpt", "thumbnail", "description");
        final Participants participants = new Participants(ownerId, Set.of(2L));
        final Study sut = new Study(content, participants, AttachedTags.empty(), LocalDateTime.now(), 10,
                LocalDate.now(), LocalDate.now(), LocalDate.now());

        assertThatThrownBy(() -> sut.kickOut(ownerId, new Participant(wrongParticipantId), LocalDate.now()))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}
