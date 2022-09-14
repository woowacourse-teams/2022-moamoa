package com.woowacourse.moamoa.studyroom.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StudyRoomTest {

    @ParameterizedTest
    @DisplayName("스터디에 참여한 참가자는 커뮤니티 게시글을 작성할 수 있다.")
    @MethodSource("provideAccessibleAccessorForCommunityArticle")
    void writeCommunityArticleByParticipant(final Accessor accessor) {
        // arrange
        final Member owner = createMember(1L);
        final Member participant = createMember(2L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner, participant);

        // act & assert
        assertThatCode(() -> studyRoom.writeCommunityArticle(accessor, "제목", "내용"))
            .doesNotThrowAnyException();
    }

    private static Stream<Arguments> provideAccessibleAccessorForCommunityArticle() {
        return Stream.of(
                Arguments.of(new Accessor(1L, 1L)), // 방장
                Arguments.of(new Accessor(2L, 1L)) // 일반 참가자
        );
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여하지 않은 참가자는 커뮤니티 게시글을 작성할 수 없다.")
    @MethodSource("provideNonAccessibleAccessorForCommunityArticle")
    void cantWriteCommunityArticleByNonParticipants(final Accessor accessor) {
        Member owner = createMember(1L);
        StudyRoom studyRoom = createStudyRoom(1L, owner);

        assertThatThrownBy(() -> studyRoom.writeCommunityArticle(accessor, "제목", "내용"))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideNonAccessibleAccessorForCommunityArticle() {
        return Stream.of(
                Arguments.of(new Accessor(2L, 1L)), // memberId가 잘못된 경우
                Arguments.of(new Accessor(1L, 2L)) // studyId가 잘못된 경우
        );
    }

    @DisplayName("방장은 공지글을 작성할 수 있다.")
    @Test
    void writeNoticeArticleByOwner() {
        // arrange
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        // act & assert
        assertThatCode(() -> studyRoom.writeNoticeArticle(new Accessor(1L, 1L), "제목", "내용"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("방장 외에는 공지글을 작성할 수 없다.")
    @MethodSource("provideNonAccessibleAccessorForNoticeArticle")
    void cantWriteNoticeArticleByNonOwner(final Accessor accessor) {
        // arrange
        final Member owner = createMember(1L);
        final Member participant = createMember(2L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner, participant);

        // act && assert
        assertThatThrownBy(() -> studyRoom.writeNoticeArticle(accessor, "제목", "내용"))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideNonAccessibleAccessorForNoticeArticle() {
        return Stream.of(
                Arguments.of(new Accessor(1L, 2L)), // studyId가 잘못된 경우
                Arguments.of(new Accessor(2L, 1L)) // 방장 외에 참여자인 경우
        );
    }

    @ParameterizedTest
    @DisplayName("링크 게시글을 스터디에 참여한 인원은 작성할 수 있다.")
    @MethodSource("provideAccessibleAccessorForLinkArticle")
    void writeLinkArticleByAccessibleAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(1L);
        final Member participant = createMember(2L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner, participant);

        // act & assert
        assertThatCode(() -> studyRoom.writeLinkArticle(accessor, "link url", "설명"))
                .doesNotThrowAnyException();
    }

    private static Stream<Arguments> provideAccessibleAccessorForLinkArticle() {
        return Stream.of(
                Arguments.of(new Accessor(1L, 1L)), // 방장
                Arguments.of(new Accessor(2L, 1L)) // 일반 참여자
        );
    }

    @ParameterizedTest
    @DisplayName("링크글을 스터디 참여자만 작성할 수 있다.")
    @MethodSource("provideNonAccessibleAccessorForLinkArticle")
    void cantWriteLinkArticleByNonAccessibleAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        // act & assert
        assertThatThrownBy(() -> studyRoom.writeLinkArticle(accessor, "link url", "설명"))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideNonAccessibleAccessorForLinkArticle() {
        return Stream.of(
                Arguments.of(new Accessor(1L, 2L)), // studyId가 잘못된 경우
                Arguments.of(new Accessor(2L, 1L)) // 스터디에 참여하지 않은 접근자
        );
    }

    private StudyRoom createStudyRoom(long studyId, Member owner, Member... participant) {
        final Set<Long> participants = Stream.of(participant)
                .map(Member::getId)
                .collect(Collectors.toSet());
        return new StudyRoom(studyId, owner.getId(), participants);
    }

    private Member createMember(final long id) {
        return new Member(id, id, "username" + id, "image", "profile");
    }
}
