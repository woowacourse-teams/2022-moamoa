package com.woowacourse.moamoa.community.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.community.service.request.ArticleRequest;
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
        final Member owner = new Member(1L, 1L, "username", "image", "profile");
        final Member another = new Member(2L, 2L, "another", "image", "profile");

        final Study study = new Study(1L,
                new Content("제목", "한 줄 소개", "http://image", "설명"),
                Participants.createBy(owner.getId()),
                new RecruitPlanner(10, RecruitStatus.RECRUITMENT_START, null),
                new StudyPlanner(LocalDate.now(), null, StudyStatus.IN_PROGRESS),
                new AttachedTags(List.of()),
                LocalDateTime.now()
        );

        assertThatThrownBy(() -> CommunityArticle.write(another, study, new ArticleRequest("제목", "내용")))
            .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("스터디와 연관된 게시물인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false"})
    void checkIsRelatedArticleWithStudy(Long targetStudyId, boolean expected) {
        final Member owner = new Member(1L, 1L, "username", "image", "profile");
        final Study study = new Study(1L,
                new Content("제목", "한 줄 소개", "http://image", "설명"),
                Participants.createBy(owner.getId()),
                new RecruitPlanner(10, RecruitStatus.RECRUITMENT_START, null),
                new StudyPlanner(LocalDate.now(), null, StudyStatus.IN_PROGRESS),
                new AttachedTags(List.of()),
                LocalDateTime.now()
        );
        final CommunityArticle communityArticle = CommunityArticle.write(owner, study, new ArticleRequest("제목", "내용"));

        assertThat(communityArticle.isBelongTo(targetStudyId)).isEqualTo(expected);
    }

    @DisplayName("스터디와 연관된 게시물인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false"})
    void checkIsAuthor(Long targetMemberId, boolean expected) {
        final Member owner = new Member(1L, 1L, "username", "image", "profile");
        final Study study = new Study(1L,
                new Content("제목", "한 줄 소개", "http://image", "설명"),
                Participants.createBy(owner.getId()),
                new RecruitPlanner(10, RecruitStatus.RECRUITMENT_START, null),
                new StudyPlanner(LocalDate.now(), null, StudyStatus.IN_PROGRESS),
                new AttachedTags(List.of()),
                LocalDateTime.now()
        );
        final CommunityArticle communityArticle = CommunityArticle.write(owner, study, new ArticleRequest("제목", "내용"));

        assertThat(communityArticle.isAuthor(targetMemberId)).isEqualTo(expected);
    }
}
