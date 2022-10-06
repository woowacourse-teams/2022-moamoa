package com.woowacourse.moamoa.studyroom.domain.article;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CommunityArticleTest {

    private static final long OWNER_ID = 1L;
    private static final long PARTICIPANT_ID = 2L;
    private static final long STUDY_ID = 1L;

    @ParameterizedTest
    @DisplayName("스터디에 참여한 참가자는 커뮤니티 게시글을 작성할 수 있다.")
    @MethodSource("providePermittedAccessor")
    void writeCommunityArticleByParticipant(final Accessor permittedAccessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final Content sut = new Content("제목", "설명");

        // act & assert
        assertThatCode(() -> Article.create(studyRoom, permittedAccessor, sut, COMMUNITY))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여하지 않은 참가자는 커뮤니티 게시글을 작성할 수 없다.")
    @MethodSource("provideForbiddenAccessor")
    void cantWriteCommunityArticleByNonParticipants(final Accessor forbiddenAccessor) {
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Content sut = new Content("제목", "설명");

        assertThatThrownBy(() -> Article.create(studyRoom, forbiddenAccessor, sut, COMMUNITY))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디에 참여한 작성자만 커뮤니티 게시글을 수정할 수 있다.")
    @Test
    void update() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Article sut = createCommunityArticle(owner, studyRoom);

        final Accessor authorAccessor = new Accessor(owner.getId(), studyRoom.getId());

        // act
        sut.update(authorAccessor, new Content("수정된 제목", "수정된 내용"));

        // assert
        assertThat(sut.getContent()).isEqualTo(new Content("수정된 제목", "수정된 내용"));
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 커뮤니티 게시글을 수정할 수 없다.")
    @MethodSource("provideForbiddenAccessor")
    void updateByNotAuthor(final Accessor forbiddenAccessor) {
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Article sut = createCommunityArticle(owner, studyRoom);

        assertThatThrownBy(() -> sut.update(forbiddenAccessor, new Content("수정된 제목", "수정된 내용")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디에 참여한 작성자만 커뮤니티 게시글을 삭제할 수 있다.")
    @Test
    void delete() {
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Article sut = createCommunityArticle(owner, studyRoom);

        final Accessor authorAccessor = new Accessor(owner.getId(), studyRoom.getId());

        sut.delete(authorAccessor);

        assertThat(sut.isDeleted()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 커뮤니티 게시글을 삭제할 수 없다.")
    @MethodSource("provideForbiddenAccessor")
    void deleteByNotAuthor(final Accessor forbiddenAccessor) {
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Article sut = createCommunityArticle(owner, studyRoom);

        assertThatThrownBy(() -> sut.delete(forbiddenAccessor))
                .isInstanceOf(UneditableArticleException.class);
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

    private Article createCommunityArticle(final Member owner, final StudyRoom studyRoom) {
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());
        final Content content = new Content("제목", "내용");
        return Article.create(studyRoom, accessor, content, COMMUNITY);
    }

    private static Stream<Arguments> providePermittedAccessor() {
        return Stream.of(
                Arguments.of(new Accessor(OWNER_ID, STUDY_ID)),
                Arguments.of(new Accessor(PARTICIPANT_ID, STUDY_ID))
        );
    }

    private static Stream<Arguments> provideForbiddenAccessor() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(otherMemberId, STUDY_ID)),
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, otherStudyId))
        );
    }
}
