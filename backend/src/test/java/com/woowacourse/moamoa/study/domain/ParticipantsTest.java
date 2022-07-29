package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParticipantsTest {

    private static Set<Participant> sampleParticipants = new HashSet<>();

    @BeforeEach
    void setUp() {
        sampleParticipants = new HashSet<>(
                List.of(new Participant(2L), new Participant(3L))
        );
    }

    @DisplayName("새로운 참여자는 스터디가 참여할 수 있다.")
    @Test
    void getFalseByNotParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, new HashSet<>(), 10L
        );

        participants.participate(participant);

        assertThat(participants.getParticipants()).isEqualTo(List.of(new Participant(1L)));
//        assertThat(participants.getRecruitStatus()).isEqualTo(RecruitStatus.OPEN);
    }

    @DisplayName("참가자라면 참을 반환한다.")
    @Test
    void getTrueByParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, Set.of(participant), 10L
        );

        assertThatThrownBy(() -> participants.participate(participant))
                .isInstanceOf(FailureParticipationException.class);
    }

    @DisplayName("스터디장은 이미 스터디에 참여한 것이므로 검사 시에 예외가 발생한다.")
    @Test
    public void checkParticipatingAboutOwner() {
        final Participants participants = new Participants(3, 10, sampleParticipants, 1L
        );

        assertThatThrownBy(() -> participants.participate(new Participant(1L)))
                .isInstanceOf(FailureParticipationException.class);
    }

    @DisplayName("이미 참여한 회원은 참여 여부 검사 시에 예외가 발생한다.")
    @Test
    public void checkAlreadyParticipating() {
        final Participants participants = new Participants(3, 10, sampleParticipants, 1L
        );

        assertThatThrownBy(() -> participants.participate(new Participant(2L)))
                .isInstanceOf(FailureParticipationException.class);
    }

    @DisplayName("스터디 회원 수가 꽉 찬 경우 예외가 발생한다.")
    @Test
    public void checkStudyMemberCount() {
        final Participants participants = new Participants(3, 3, sampleParticipants, 1L
        );

        assertThatThrownBy(() -> participants.participate(new Participant(4L)))
                .isInstanceOf(FailureParticipationException.class);
    }

    @DisplayName("마지막 인원이 참여시 스터디 모집은 종료된다.")
    @Test
    public void IncreaseCurrentMemberCount() {
        final Participants participants = new Participants(3, 4, sampleParticipants, 1L
        );
        final Participant participant = new Participant(4L);

        participants.participate(participant);

        assertThat(participants.getParticipants()).containsExactlyInAnyOrder(new Participant(2L), new Participant(3L), new Participant(4L));
//        assertThat(participants.getRecruitStatus()).isEqualTo(CLOSE);
    }
}
