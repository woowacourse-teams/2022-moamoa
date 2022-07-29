package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.CLOSE;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("참가자가 아니라면 거짓을 반환한다.")
    @Test
    void getFalseByNotParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, Set.of(), 10L, OPEN);

        assertThat(participants.contains(participant)).isFalse();
    }

    @DisplayName("참가자라면 참을 반환한다.")
    @Test
    void getTrueByParticipatedMember() {
        final Participant participant = new Participant(1L);
        final Participants participants = new Participants(1, 3, Set.of(participant), 10L, OPEN);

        assertThat(participants.contains(participant)).isTrue();
    }

    @DisplayName("스터디장은 이미 스터디에 참여한 것이므로 검사 시에 예외가 발생한다.")
    @Test
    public void checkParticipatingAboutOwner() {
        final Participants participants = new Participants(3, 10, sampleParticipants, 1L, OPEN);

        assertThat(participants.isImpossibleParticipation(1L)).isTrue();
    }

    @DisplayName("이미 참여한 회원은 참여 여부 검사 시에 예외가 발생한다.")
    @Test
    public void checkAlreadyParticipating() {
        final Participants participants = new Participants(3, 10, sampleParticipants, 1L, OPEN);

        assertThat(participants.isImpossibleParticipation(2L)).isTrue();
    }

    @DisplayName("스터디 회원 수가 꽉 차지 않은 경우 정상적으로 가입이 된다.")
    @Test
    public void checkStudyMemberCountLessThanMaxCount() {
        final Participants participants = new Participants(3, 4, sampleParticipants, 1L, OPEN);

        assertThat(participants.isImpossibleParticipation(4L)).isFalse();
    }

    @DisplayName("스터디 회원 수가 꽉 찬 경우 예외가 발생한다.")
    @Test
    public void checkStudyMemberCount() {
        final Participants participants = new Participants(3, 3, sampleParticipants, 1L, OPEN);

        assertThat(participants.isImpossibleParticipation(4L)).isTrue();
    }

    @DisplayName("스터디 참여 이후에는 현재 스터디원 수가 증가한다.")
    @Test
    public void IncreaseCurrentMemberCount() {
        final Participants participants = new Participants(3, 4, sampleParticipants, 1L, OPEN);
        final Participant participant = new Participant(4L);

        participants.participate(participant);

        assertThat(participants.getCurrentMemberSize()).isEqualTo(4);
        assertThat(participants.isImpossibleParticipation(4L)).isTrue();
    }

    @DisplayName("스터디가 가득차면 CLOSE 상태로 바뀐다.")
    @Test
    public void changeWhenFullStudy() {
        final Participants participants = new Participants(3, 4, sampleParticipants, 1L, OPEN);
        final Participant participant = new Participant(4L);

        participants.participate(participant);

        assertThat(participants.getRecruitStatus()).isEqualTo(CLOSE);
    }
}
