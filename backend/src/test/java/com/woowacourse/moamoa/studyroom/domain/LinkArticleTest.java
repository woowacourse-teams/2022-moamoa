package com.woowacourse.moamoa.studyroom.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.service.exception.NotLinkAuthorException;
import com.woowacourse.moamoa.studyroom.service.exception.NotRelatedLinkException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkArticleTest {

    @DisplayName("링크 공유를 수정한다.")
    @Test
    void update() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");
        final LinkArticle updatedLinkArticle = studyRoom
                .writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                        "updated link", "수정된 설명");

        linkArticle.update(updatedLinkArticle);

        assertAll(
                () -> assertThat(linkArticle.getLinkUrl()).isEqualTo("updated link"),
                () -> assertThat(linkArticle.getDescription()).isEqualTo("수정된 설명")
        );
    }

    @DisplayName("작성자가 아니면 수정할 수 없다.")
    @Test
    void updateByNotAuthor() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");

        final LinkArticle updatedLinkArticle = new LinkArticle(studyRoom, 2L, "updated link",
                "수정된 설명");

        assertThatThrownBy(() -> linkArticle.update(updatedLinkArticle))
                .isInstanceOf(NotLinkAuthorException.class);
    }

    @DisplayName("스터디에 속하지 않은 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotBelongToStudy() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);
        final StudyRoom anotherStudyRoom = createStudyRoom(2L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");

        final LinkArticle updatedLinkArticle = anotherStudyRoom.writeLinkArticle(new Accessor(owner.getId(), anotherStudyRoom.getId()),
                        "updated link", "수정된 설명");

        assertThatThrownBy(() -> linkArticle.update(updatedLinkArticle))
                .isInstanceOf(NotRelatedLinkException.class);
    }

    @DisplayName("링크 공유를 삭제한다.")
    @Test
    void delete() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");

        linkArticle.delete(1L, 1L);

        assertThat(linkArticle.isDeleted()).isTrue();
    }

    @DisplayName("작성자가 아니면 삭제할 수 없다.")
    @Test
    void deleteByNotAuthor() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");

        assertThatThrownBy(() -> linkArticle.delete(2L, 1L))
                .isInstanceOf(NotLinkAuthorException.class);
    }

    @DisplayName("스터디에 속하지 않은 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotBelongToStudy() {
        final Member owner = createMember(1L);
        final StudyRoom studyRoom = createStudyRoom(1L, owner);

        final LinkArticle linkArticle = studyRoom.writeLinkArticle(new Accessor(owner.getId(), studyRoom.getId()),
                "link", "설명");

        assertThatThrownBy(() -> linkArticle.delete(1L, 2L))
                .isInstanceOf(NotRelatedLinkException.class);
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
