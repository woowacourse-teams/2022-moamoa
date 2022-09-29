package com.woowacourse.moamoa.studyroom.domain.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

class LinkArticleTest {

    private static final long OWNER_ID = 1L;
    private static final long STUDY_ID = 1L;

    @DisplayName("링크 게시글을 작성자가 수정한다.")
    @Test
    void update() {
        // arrange
        final Member owner = createOwner();
        final StudyRoom studyRoom = createStudyRoom(owner);
        final LinkArticle sut = createLinkArticle(owner, studyRoom);
        final Accessor authorAccessor = new Accessor(owner.getId(), studyRoom.getId());

        // act
        sut.update(authorAccessor, new LinkContent("updated link", "수정된 설명"));

        // assert
        assertThat(sut.getContent()).isEqualTo(new LinkContent("updated link", "수정된 설명"));
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 링크 게시글을 수정할 수 없다.")
    @MethodSource("provideForbiddenAccessor")
    void updateByNotAuthor(final Accessor forbiddenAccessor) {
        final Member owner = createOwner();
        final StudyRoom studyRoom = createStudyRoom(owner);
        final LinkArticle sut = createLinkArticle(owner, studyRoom);

        assertThatThrownBy(() -> sut.update(forbiddenAccessor, new LinkContent("updated link", "수정된 설명")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디에 참여한 작성자만 링크 게시글을 삭제할 수 있다.")
    @Test
    void delete() {
        final Member owner = createOwner();
        final StudyRoom studyRoom = createStudyRoom(owner);
        final LinkArticle sut = createLinkArticle(owner, studyRoom);

        sut.delete(new Accessor(1L, 1L));

        assertThat(sut.isDeleted()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 링크 게시글을 삭제할 수 없다.")
    @MethodSource("provideForbiddenAccessor")
    void deleteByNotAuthor(final Accessor forbiddenAccessor) {
        final Member owner = createOwner();
        final StudyRoom studyRoom = createStudyRoom(owner);
        final LinkArticle sut = createLinkArticle(owner, studyRoom);

        assertThatThrownBy(() -> sut.delete(forbiddenAccessor))
                .isInstanceOf(UneditableArticleException.class);
    }

    private static Stream<Arguments> provideForbiddenAccessor() {
        final long otherMemberId = OWNER_ID + 1;
        final long otherStudyId = STUDY_ID + 1;

        return Stream.of(
                Arguments.of(new Accessor(otherMemberId, STUDY_ID)),
                Arguments.of(new Accessor(OWNER_ID, otherStudyId)),
                Arguments.of(new Accessor(otherMemberId, otherStudyId))
        );
    }

    private Member createOwner() {
        return new Member(OWNER_ID, OWNER_ID, "owner", "image", "profile");
    }

    private StudyRoom createStudyRoom(Member owner, Member... participant) {
        final Set<Long> participants = Stream.of(participant)
                .map(Member::getId)
                .collect(Collectors.toSet());
        return new StudyRoom(STUDY_ID, owner.getId(), participants);
    }

    private LinkArticle createLinkArticle(final Member owner, final StudyRoom studyRoom) {
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());
        final LinkContent linkContent = new LinkContent("link", "설명");
        return linkContent.createArticle(studyRoom, accessor);
    }
}
