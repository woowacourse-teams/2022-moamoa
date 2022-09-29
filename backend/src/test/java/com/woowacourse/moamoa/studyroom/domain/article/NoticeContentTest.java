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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NoticeContentTest {

    @DisplayName("방장은 공지글을 작성할 수 있다.")
    @Test
    void writeNoticeArticleByOwner() {
        // arrange
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final NoticeContent sut = new NoticeContent("제목", "내용");

        // act & assert
        assertThatCode(() -> sut.createArticle(studyRoom, new Accessor(1L, 1L)))
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
        final NoticeContent sut = new NoticeContent("제목", "내용");

        // act && assert
        assertThatThrownBy(() -> sut.createArticle(studyRoom, accessor))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideNonAccessibleAccessorForNoticeArticle() {
        return Stream.of(
                Arguments.of(new Accessor(1L, 2L)), // studyId가 잘못된 경우
                Arguments.of(new Accessor(2L, 1L)) // 방장 외에 참여자인 경우
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