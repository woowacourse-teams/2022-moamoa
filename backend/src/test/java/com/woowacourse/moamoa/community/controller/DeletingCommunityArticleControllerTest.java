package com.woowacourse.moamoa.community.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.service.CommunityArticleService;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.community.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class DeletingCommunityArticleControllerTest {

    CreatingStudyRequestBuilder javaStudyRequest = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommunityArticleRepository communityArticleRepository;

    @Autowired
    private CommunityArticleDao communityArticleDao;

    private StudyService studyService;
    private CommunityArticleController sut;
    private CommunityArticleService communityArticleService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        communityArticleService = new CommunityArticleService(memberRepository, studyRepository,
                communityArticleRepository, communityArticleDao);
        sut = new CommunityArticleController(communityArticleService);
    }

    @DisplayName("스터디 커뮤니티 게시글을 삭제한다.")
    @Test
    void deleteCommunityArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        CommunityArticle article = communityArticleService.createArticle(member.getId(), study.getId(),
                request);

        //act
        sut.deleteArticle(member.getId(), study.getId(), article.getId());

        //assert
        assertThat(communityArticleRepository.existsById(article.getId())).isFalse();
    }

    @DisplayName("게시글이 없는 경우 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingToNotFoundArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        final Long memberId = member.getId();
        final Long studyId = study.getId();

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(memberId, studyId, 1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("게시글을 삭제할 수 없는 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenDeletingByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getId(), study.getId(),
                request);

        final Long otherId = other.getId();
        final Long studyId = study.getId();
        final Long articleId = article.getId();

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(otherId, studyId, articleId))
                .isInstanceOf(UneditableArticleException.class);
    }
}
