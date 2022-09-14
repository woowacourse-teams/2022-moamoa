package com.woowacourse.moamoa.studyroom.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyRoomTest {

    @DisplayName("스터디에 참여한 방장은 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByOwner() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Set<Long> participants = Set.of(2L, 3L);
        final Accessor accessor = new Accessor(ownerId, studyId);
        final StudyRoom sut = new StudyRoom(studyId, ownerId, participants);

        assertDoesNotThrow(() -> sut.writeReview(accessor, "content"));
    }

    @DisplayName("스터디에 참여한 사용자는 리뷰를 작성할 수 있다.")
    @Test
    void writeReviewByParticipant() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Long memberId = 2L;
        final Set<Long> participants = Set.of(memberId, 3L);
        final Accessor accessor = new Accessor(memberId, studyId);
        final StudyRoom sut = new StudyRoom(studyId, ownerId, participants);

        assertDoesNotThrow(() -> sut.writeReview(accessor, "content"));
    }

    @DisplayName("스터디에 참여하지 않은 사용자는 리뷰를 작성할 수 없다.")
    @Test
    void writeReviewByNotParticipant() {
        final Long studyId = 1L;
        final Long ownerId = 1L;
        final Set<Long> participants = Set.of(2L, 3L);
        final Long wrongMemberId = 4L;
        final Accessor accessor = new Accessor(wrongMemberId, studyId);
        final StudyRoom sut = new StudyRoom(studyId, ownerId, participants);

        assertThatThrownBy(() -> sut.writeReview(accessor, "content"))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}