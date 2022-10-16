package com.woowacourse.moamoa.studyroom.domain.article;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.COMMUNITY;
import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TempArticleTest {

    private static final long OWNER_ID = 1L;
    private static final long PARTICIPANT_ID = 2L;
    private static final long STUDY_ID = 1L;

    @DisplayName("방장은 임시 공지글을 작성할 수 있다.")
    @Test
    void writeNoticeArticleByOwner() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());

        // act & assert
        assertThatCode(() -> createNoticeTempArticle(studyRoom, accessor, "제목", "내용"))
                .doesNotThrowAnyException();
    }

    @DisplayName("방장 외에는 공지글을 작성할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForNotice")
    void cantWriteNoticeArticleByNonOwner(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);

        // act && assert
        assertThatThrownBy(() -> createNoticeTempArticle(studyRoom, accessor, "제목", "내용"))
                .isInstanceOf(UnwritableException.class);
    }

    private static Stream<Arguments> provideForbiddenAccessorForNotice() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(PARTICIPANT_ID, STUDY_ID)),
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, otherStudyId))
        );
    }

    @DisplayName("방장은 커뮤니티 임시 게시글을 작성할 수 있다.")
    @Test
    void writeCommunityArticleByOwner() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());

        // act && assert
        assertThatCode(() -> createCommunityTempArticle(studyRoom, accessor, "제목", "내용"))
                .doesNotThrowAnyException();
    }

    @DisplayName("참여자는 커뮤니티 임시 게시글을 작성할 수 있다.")
    @Test
    void writeCommunityArticleByParticipant() {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final Accessor accessor = new Accessor(participant.getId(), studyRoom.getId());

        // act && assert
        assertThatCode(() -> createCommunityTempArticle(studyRoom, accessor, "제목", "내용"))
                .doesNotThrowAnyException();
    }

    @DisplayName("참여자 외에는 커뮤니티 임시 게시글을 작성할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForCommunity")
    void writeCommunityArticleByOtherMember(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);

        // act && assert
        assertThatCode(() -> createCommunityTempArticle(studyRoom, accessor, "제목", "내용"))
                .isInstanceOf(UnwritableException.class);
    }

    private static Stream<Arguments> provideForbiddenAccessorForCommunity() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, STUDY_ID)),
                Arguments.of(new Accessor(otherMemberId, otherStudyId))
        );
    }

    @DisplayName("작성자만 임시글에 대한 권한을 가질 수 있다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void viewableByAuthorAccessor(final ArticleType type) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final StudyRoom studyRoom = createStudyRoom(owner);
        final TempArticle tempArticle = createTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), type, new Content("제목", "내용")
        );

        // act && assert
        assertThat(tempArticle.isForbiddenAccessor(new Accessor(owner.getId(), studyRoom.getId()))).isFalse();
    }

    @DisplayName("작성자외의 사용자는 공지 임시글에 대한 권한을 가질 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForEdit")
    void forbiddenToNoticeByOtherAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = createNoticeTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용"
        );

        // act && assert
        assertThat(tempArticle.isForbiddenAccessor(accessor)).isTrue();
    }

    @DisplayName("작성자 외의 사용자는 공지 커뮤니티글에 대한 권한을 가질 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForEdit")
    void forbiddenToCommunityByOtherAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = createCommunityTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용"
        );

        // act && assert
        assertThat(tempArticle.isForbiddenAccessor(accessor)).isTrue();
    }

    @DisplayName("작성자는 임시글을 수정할 수 있다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateByAuthor(final ArticleType type) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = createTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), type, new Content("제목", "내용")
        );

        // act
        tempArticle.update(new Accessor(owner.getId(), studyRoom.getId()), new Content("수정된 제목", "수정된 내용"));

        // assert
        assertThat(tempArticle.getTitle()).isEqualTo("수정된 제목");
        assertThat(tempArticle.getContent()).isEqualTo("수정된 내용");
    }
    @DisplayName("작성자 외의 사용자는 임시 공지글을 수정할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForEdit")
    void updateNoticeByForbiddenAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = createNoticeTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용"
        );

        // act && assert
        assertThatCode(() -> tempArticle.update(accessor, new Content("수정된 제목", "수정된 내용")))
            .isInstanceOf(UneditableException.class);
    }

    @DisplayName("작성자 외의 사용자는 임시 커뮤니티글을 수정할 수 없다.")
    @ParameterizedTest
    @MethodSource("provideForbiddenAccessorForEdit")
    void updateCommunityByForbiddenAccessor(final Accessor accessor) {
        // arrange
        final Member owner = createMember(OWNER_ID);
        final Member participant = createMember(PARTICIPANT_ID);
        final StudyRoom studyRoom = createStudyRoom(owner, participant);
        final TempArticle tempArticle = createCommunityTempArticle(
                studyRoom, new Accessor(owner.getId(), studyRoom.getId()), "제목", "내용"
        );

        // act && assert
        assertThatCode(() -> tempArticle.update(accessor, new Content("수정된 제목", "수정된 내용")))
                .isInstanceOf(UneditableException.class);
    }

    private static Stream<Arguments> provideForbiddenAccessorForEdit() {
        final long otherMemberId = Math.max(OWNER_ID, PARTICIPANT_ID) + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(PARTICIPANT_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, STUDY_ID)),
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

    private TempArticle createNoticeTempArticle(
            final StudyRoom studyRoom, final Accessor accessor, final String title, final String content
    ) {
        return createTempArticle(studyRoom, accessor, NOTICE, new Content(title, content));
    }

    private TempArticle createCommunityTempArticle(
            final StudyRoom studyRoom, final Accessor accessor, final String title, final String content
    ){
        return createTempArticle(studyRoom, accessor, COMMUNITY, new Content(title, content));
    }

    private TempArticle createTempArticle(
            final StudyRoom studyRoom, final Accessor accessor, final ArticleType type, final Content content
    ) {
        return TempArticle.create(content, studyRoom, accessor, type);
    }
}
