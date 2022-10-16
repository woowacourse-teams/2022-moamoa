package com.woowacourse.moamoa.studyroom.domain.article;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TempArticleTest {

    private static final long OWNER_ID = 1L;
    private static final long PARTICIPANT_ID = 2L;
    private static final long STUDY_ID = 1L;

    @DisplayName("방장은 공지글을 작성할 수 있다.")
    @Test
    void writeNoticeArticleByOwner() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());

        // act & assert
        assertThatCode(() -> TempArticle.create(studyRoom, accessor, "제목", "내용", NOTICE))
                .doesNotThrowAnyException();
    }

    @DisplayName("방장 외에는 공지글을 작성할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessor")
    void cantWriteNoticeArticleByNonOwner(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);

        // act && assert
        assertThatThrownBy(() -> TempArticle.create(studyRoom, accessor, "제목", "내용", NOTICE))
                .isInstanceOf(UnwritableException.class);
    }

    @DisplayName("작성자만 임시글에 대한 권한을 가질 수 있다.")
    @Test
    void viewableByAuthorAccessor() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final TempArticle tempArticle = TempArticle.create(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용", NOTICE
        );

        // act && assert
        assertThat(tempArticle.isForbiddenAccessor(new Accessor(owner.getId(), studyRoom.getId()))).isFalse();
    }

    @DisplayName("작성자외의 사용자는 임시글에 대한 권한을 가질 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessor")
    void unviewableByOtherAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = TempArticle.create(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용", NOTICE
        );

        // act && assert
        assertThat(tempArticle.isForbiddenAccessor(accessor)).isTrue();
    }

    private static Stream<Arguments> provideForbiddenAccessor() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(PARTICIPANT_ID, STUDY_ID)),
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, otherStudyId))
        );
    }

    private Member createMember(final long id) {
        return new Member(id, id, "username" + id, "image", "profile");
    }

    private StudyRoom createStudyRoom(Member owner, Member... participant) {
        final Set<Long> participants = Stream.of(participant)
                .map(Member::getId)
                .collect(Collectors.toSet());
        return new StudyRoom(STUDY_ID, owner.getId(), participants);
    }
}
