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
import org.junit.jupiter.params.provider.CsvSource;

class LinkArticleTest {

    @DisplayName("링크 게시글을 작성자가 수정한다.")
    @Test
    void update() {
        // arrange
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());
        final LinkContent linkContent = new LinkContent("link", "설명");
        final LinkArticle sut = linkContent.createArticle(studyRoom, accessor);

        // act
        sut.update(accessor, new LinkContent("updated link", "수정된 설명"));

        // assert
        assertThat(sut.getContent()).isEqualTo(new LinkContent("updated link", "수정된 설명"));
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 링크 게시글을 수정할 수 없다.")
    @CsvSource({"2,1", "1,2"})
    void updateByNotAuthor(final long memberId, final long studyId) {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final LinkContent linkContent = new LinkContent("link", "설명");
        final LinkArticle sut = linkContent.createArticle(studyRoom, new Accessor(owner.getId(), studyRoom.getId()));

        assertThatThrownBy(() -> sut.update(new Accessor(memberId, studyId), new LinkContent("updated link", "수정된 설명")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디에 참여한 작성자만 링크 게시글을 삭제할 수 있다.")
    @Test
    void delete() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final LinkContent linkContent = new LinkContent("link", "설명");
        final LinkArticle sut = linkContent.createArticle(studyRoom, new Accessor(owner.getId(), studyRoom.getId()));

        sut.delete(new Accessor(1L, 1L));

        assertThat(sut.isDeleted()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 링크 게시글을 삭제할 수 없다.")
    @CsvSource({"2,1", "1,2"})
    void deleteByNotAuthor(final long memberId, final long studyId) {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final LinkContent linkContent = new LinkContent("link", "설명");
        final LinkArticle sut = linkContent.createArticle(studyRoom, new Accessor(owner.getId(), studyRoom.getId()));

        assertThatThrownBy(() -> sut.delete(new Accessor(memberId, studyId)))
                .isInstanceOf(UneditableArticleException.class);
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
