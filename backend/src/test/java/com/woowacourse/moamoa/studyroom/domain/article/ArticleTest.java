package com.woowacourse.moamoa.studyroom.domain.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ArticleTest {

    @DisplayName("스터디 참여자는 게시글을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,true", "3,false"})
    void getArticle(Long viewerId, boolean expected) {
        long studyId = 1L;
        Member owner = createMember(1L);
        Member participant = createMember(2L);
        StudyRoom studyRoom = createPermittedAccessors(studyId, owner, participant);
        Accessor accessor = new Accessor(owner.getId(), studyId);
        Article article = studyRoom.writeNoticeArticle(accessor, "제목", "내용");

        assertThat(article.isViewableBy(new Accessor(viewerId, studyId))).isEqualTo(expected);
    }

    @DisplayName("스터디에 속해 있는 게시글이 맞을 경우 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false"})
    void deleteArticle(Long studyId, Long wantToViewStudyId, boolean expected) {
        Member member = createMember(1L);
        StudyRoom studyRoom = createPermittedAccessors(studyId, member);
        Accessor accessor = new Accessor(member.getId(), studyId);
        Article article = studyRoom.writeNoticeArticle(accessor, "제목", "내용");

        assertThat(article.isViewableBy(new Accessor(member.getId(), wantToViewStudyId))).isEqualTo(expected);
    }

    @DisplayName("스터디에 참여했고, 작성자인 경우 스터디 게시글을 수정,삭제할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false", "3,false"})
    void updateArticle(Long editorId, boolean expected) {
        Member owner = createMember(1L);
        Member participant = createMember(2L);
        StudyRoom studyRoom = createPermittedAccessors(1L, owner, participant);
        Accessor accessor = new Accessor(owner.getId(), 1L);
        Article article = studyRoom.writeNoticeArticle(accessor, "제목", "내용");

        assertThat(article.isEditableBy(new Accessor(editorId, 1L))).isEqualTo(expected);
    }

    @DisplayName("스터디에 속해 있지 않은 게시글인 경우 수정,삭제할 수 없다.")
    @Test
    void editArticleByInvalidStudyId() {
        Member member = createMember(1L);
        StudyRoom studyRoom = createPermittedAccessors(1L, member);
        Accessor accessor = new Accessor(member.getId(), 1L);
        Article article = studyRoom.writeNoticeArticle(accessor, "제목", "내용");

        assertThat(article.isViewableBy(new Accessor(member.getId(), 2L))).isFalse();
    }

    private StudyRoom createPermittedAccessors(long studyId, Member owner, Member... participant) {
        final Set<Long> participants = Stream.of(participant).map(Member::getId).collect(Collectors.toSet());
        return new StudyRoom(studyId, owner.getId(), participants);
    }

    private Member createMember(final long id) {
        return new Member(id, id, "username" + id, "image", "profile");
    }
}
