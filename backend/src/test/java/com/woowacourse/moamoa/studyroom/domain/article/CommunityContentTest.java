package com.woowacourse.moamoa.studyroom.domain.article;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommunityContentTest {

    private static final long OWNER_ID = 1L;
    private static final long PARTICIPANT_ID = 2L;
    private static final long STUDY_ID = 1L;

    @ParameterizedTest
    @DisplayName("스터디에 참여한 참가자는 커뮤니티 게시글을 작성할 수 있다.")
    @MethodSource("providePermittedAccessorForCommunityArticle")
    void writeCommunityArticleByParticipant(final Accessor permittedAccessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final CommunityContent sut = new CommunityContent("제목", "설명");

        // act & assert
        assertThatCode(() -> sut.createArticle(studyRoom, permittedAccessor))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> providePermittedAccessorForCommunityArticle() {
        return Stream.of(
                Arguments.of(new Accessor(OWNER_ID, STUDY_ID)),
                Arguments.of(new Accessor(PARTICIPANT_ID, STUDY_ID))
        );
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여하지 않은 참가자는 커뮤니티 게시글을 작성할 수 없다.")
    @MethodSource("provideForbiddenAccessorForCommunityArticle")
    void cantWriteCommunityArticleByNonParticipants(final Accessor forbiddenAccessor) {
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final CommunityContent sut = new CommunityContent("제목", "설명");

        assertThatThrownBy(() -> sut.createArticle(studyRoom, forbiddenAccessor))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideForbiddenAccessorForCommunityArticle() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(otherMemberId, STUDY_ID)), // memberId가 잘못된 경우
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)) // studyId가 잘못된 경우
        );
    }

    private StudyRoom createStudyRoom(Member owner, Member... participant) {
        final Set<Long> participants = Stream.of(participant)
                .map(Member::getId)
                .collect(Collectors.toSet());
        return new StudyRoom(STUDY_ID, owner.getId(), participants);
    }

    private Member createMember(final long id) {
        return new Member(id, id, "username" + id, "image", "profile");
    }
}