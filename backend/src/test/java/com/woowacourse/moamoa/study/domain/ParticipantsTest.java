package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParticipantsTest {

    private static final List<Participant> PARTICIPANTS = new ArrayList<>(
            List.of(new Participant(2L), new Participant(3L), new Participant(4L))
    );

    @DisplayName("스터디장은 이미 스터디에 참여한 것이므로 검사 시에 예외가 발생한다.")
    @Test
    public void checkParticipatingAboutOwner() {
        final Participants participants = new Participants(3, 10, PARTICIPANTS, 1L);

        assertThatThrownBy(() -> participants.checkParticipating(1L));
    }

    @DisplayName("이미 참여한 회원은 참여 여부 검사 시에 예외가 발생한다.")
    @Test
    public void checkAlreadyParticipating() {
        final Participants participants = new Participants(3, 10, PARTICIPANTS, 1L);

        assertThatThrownBy(() -> participants.checkParticipating(1L));
    }

    @DisplayName("스터디 회원 수가 꽉 차지 않은 경우 정상적으로 가입이 된다.")
    @Test
    public void checkStudyMemberCountLessThanMaxCount() {
        final Participants participants = new Participants(3, 4, PARTICIPANTS, 1L);

        assertDoesNotThrow(() -> participants.checkParticipating(4L));
    }

    @DisplayName("스터디 회원 수가 꽉 찬 경우 예외가 발생한다.")
    @Test
    public void checkStudyMemberCount() {
        final Participants participants = new Participants(3, 3, PARTICIPANTS, 1L);

        assertThatThrownBy(() -> participants.checkParticipating(4L));
    }
    
    @DisplayName("스터디 참여 이후에는 현재 스터디원 수가 증가한다.")
    @Test
    public void IncreaseCurrentMemberCount() {
        final Participants participants = new Participants(3, 4, PARTICIPANTS, 1L);

        participants.participate(new Participant(4L));

        assertThat(participants.getParticipants().size()).isEqualTo(4);
        assertThatThrownBy(() -> participants.checkParticipating(5L));
    }
}
