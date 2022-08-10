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
import com.woowacourse.moamoa.community.service.exception.NotArticleAuthorException;
import com.woowacourse.moamoa.community.service.exception.NotRelatedArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class DeletingCommunityArticleControllerTest {

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
        CommunityArticle article = communityArticleService.createArticle(member.getGithubId(), study.getId(),
                request);

        //act
        sut.deleteArticle(member.getGithubId(), study.getId(), article.getId());

        //assert
        assertThat(communityArticleRepository.existsById(article.getId())).isFalse();
    }

    @DisplayName("존재하지 않는 사용자가 게시글 삭제 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGetByNotFoundMember() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());
        CommunityArticle article = communityArticleService
                .createArticle(member.getGithubId(), study.getId(), new ArticleRequest("제목", "내용"));

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(2L, study.getId(), article.getId()))
                .isInstanceOf(MemberNotFoundException.class);
        assertThat(communityArticleRepository.existsById(article.getId())).isTrue();
    }

    @DisplayName("스터디가 없는 경우 게시글 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());
        CommunityArticle article = communityArticleService
                .createArticle(member.getGithubId(), study.getId(), new ArticleRequest("제목", "내용"));
        long notFoundStudyId = study.getId() + 1L;

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(member.getGithubId(), notFoundStudyId, article.getId()))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("게시글이 없는 경우 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingToNotFoundArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(member.getGithubId(), study.getId(), 1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 사용자가 스터디 커뮤니티 게시글을 삭제한 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenDeletingByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getGithubId(), study.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(other.getGithubId(), study.getId(), article.getId()))
                .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("스터디와 연관되지 않은 게시글 삭제 시 예외 발생")
    @Test
    void throwExceptionWhenDeletingNotRelatedArticleWithStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        Study hasArticleStudy = studyService.createStudy(member.getGithubId(),
                javaStudyRequest.startDate(LocalDate.now()).build());
        Study notHasArticleStudy = studyService.createStudy(member.getGithubId(),
                javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getGithubId(), hasArticleStudy.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(member.getGithubId(), notHasArticleStudy.getId(), article.getId()))
                .isInstanceOf(NotRelatedArticleException.class);
    }

    @DisplayName("작성자 외에 게시글 삭제 시 예외 발생")
    @Test
    void throwExceptionWhenDeletingNotAuthor() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "other", "imageUrl", "profileUrl"));

        Study study = studyService.createStudy(member.getGithubId(),
                javaStudyRequest.startDate(LocalDate.now()).build());
        studyService.participateStudy(other.getGithubId(), study.getId());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getGithubId(), study.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(other.getGithubId(), study.getId(), article.getId()))
                .isInstanceOf(NotArticleAuthorException.class);
    }
}
