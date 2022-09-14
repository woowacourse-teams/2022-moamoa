package com.woowacourse.moamoa.studyroom.domain.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CommunityArticleTest {

    @DisplayName("스터디에 참여한 작성자만 커뮤니티 게시글을 수정할 수 있다.")
    @Test
    void update() {
        // arrange
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final Accessor accessor = new Accessor(owner.getId(), studyRoom.getId());
        final CommunityArticle sut = studyRoom.writeCommunityArticle(accessor, "제목", "내용");

        // act
        sut.update(accessor, "수정된 제목", "수정된 내용");

        // assert
        assertAll(
                () -> assertThat(sut.getTitle()).isEqualTo("수정된 제목"),
                () -> assertThat(sut.getContent()).isEqualTo("수정된 내용")
        );
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 커뮤니티 게시글을 수정할 수 없다.")
    @CsvSource({"2,1", "1,2"})
    void updateByNotAuthor(final long memberId, final long studyId) {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final CommunityArticle sut = studyRoom.writeCommunityArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "제목", "내용");

        assertThatThrownBy(() -> sut.update(new Accessor(memberId, studyId), "수정된 제목", "수정된 내용"))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디에 참여한 작성자만 커뮤니티 게시글을 삭제할 수 있다.")
    @Test
    void delete() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final CommunityArticle sut = studyRoom.writeCommunityArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "제목", "내용");

        sut.delete(new Accessor(1L, 1L));

        assertThat(sut.isDeleted()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("스터디에 참여한 작성자 외에는 커뮤니티 게시글을 삭제할 수 없다.")
    @CsvSource({"2,1", "1,2"})
    void deleteByNotAuthor(final long memberId, final long studyId) {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final CommunityArticle sut = studyRoom.writeCommunityArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "제목", "내용");

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
