package com.woowacourse.moamoa.studyroom.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.RecruitStatus;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.StudyStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CommunityArticleTest {

    @DisplayName("스터디에 참여한 참가자만 게시글을 작성할 수 있다.")
    @Test
    void writeCommunityArticleByParticipant() {
        final Member owner = createMember(1L);
        final Member another = createMember(2L);
        final Study study = createStudy(1L, owner);

        assertThatThrownBy(() -> Article.write(another.getId(), study, "제목", "내용", ArticleType.COMMUNITY))
            .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("스터디 참여자는 게시글을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,true", "3,false"})
    void getArticle(Long viewerId, boolean expected) {
        final Member owner = createMember(1L);
        final Member participant = createMember(2L);
        final Study study = createStudy(1L, owner);
        study.participate(participant.getId());

        final Article communityArticle = Article.write(owner.getId(), study, "제목", "내용", ArticleType.COMMUNITY);

        assertThat(communityArticle.isViewableBy(new Accessor(viewerId, study.getId()))).isEqualTo(expected);
    }

    @DisplayName("스터디에 속해 있는 게시글이 맞을 경우 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false"})
    void deleteArticle(Long studyId, Long wantToViewStudyId, boolean expected) {
        final Member member = createMember(1L);
        final Study study = createStudy(studyId, member);
        final Article communityArticle = Article.write(member.getId(), study, "제목", "내용", ArticleType.COMMUNITY);

        assertThat(communityArticle.isViewableBy(new Accessor(member.getId(), wantToViewStudyId))).isEqualTo(expected);
    }

    @DisplayName("스터디에 참여했고, 작성자인 경우 스터디 게시글을 수정,삭제할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false", "3,false"})
    void updateArticle(Long editorId, boolean expected) {
        final Member owner = createMember(1L);
        final Member participant = createMember(2L);
        final Study study = createStudy(1L, owner);
        study.participate(participant.getId());
        final Article communityArticle = Article.write(owner.getId(), study, "제목", "내용", ArticleType.COMMUNITY);

        assertThat(communityArticle.isEditableBy(new Accessor(editorId, study.getId()))).isEqualTo(expected);
    }

    @DisplayName("스터디에 속해 있지 않은 게시글인 경우 수정,삭제할 수 없다.")
    @Test
    void editArticleByInvalidStudyId() {
        final Member member = createMember(1L);
        final Study study = createStudy(1L, member);
        final Article communityArticle = Article.write(member.getId(), study, "제목", "내용", ArticleType.COMMUNITY);

        assertThat(communityArticle.isViewableBy(new Accessor(member.getId(), 2L))).isFalse();
    }

    private Study createStudy(final long id, final Member owner) {
        return new Study(id,
                new Content("제목", "한 줄 소개", "http://image", "설명"),
                Participants.createBy(owner.getId()),
                new RecruitPlanner(10, RecruitStatus.RECRUITMENT_START, null),
                new StudyPlanner(LocalDate.now(), null, StudyStatus.IN_PROGRESS),
                new AttachedTags(List.of()),
                LocalDateTime.now()
        );
    }

    private Member createMember(final long id) {
        return new Member(id, id, "username" + id, "image", "profile");
    }
}
