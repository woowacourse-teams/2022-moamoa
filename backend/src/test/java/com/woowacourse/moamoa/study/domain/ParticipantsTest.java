package com.woowacourse.moamoa.study.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParticipantsTest {

    @DisplayName("참가자가 아니라면 거짓을 반환한다.")
    @Test
    void getFalseByNotParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, List.of(), 10L);

        assertThat(participants.contains(participant)).isFalse();
    }

    @DisplayName("참가자라면 참을 반환한다.")
    @Test
    void getTrueByParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, List.of(participant), 10L);

        assertThat(participants.contains(participant)).isTrue();
    }
}