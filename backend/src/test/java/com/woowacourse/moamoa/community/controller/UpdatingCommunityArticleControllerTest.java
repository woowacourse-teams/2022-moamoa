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
import com.woowacourse.moamoa.community.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class UpdatingCommunityArticleControllerTest {

    CreatingStudyRequestBuilder javaStudyBuilder = new CreatingStudyRequestBuilder()
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

    @DisplayName("게시글을 수정한다.")
    @Test
    void updateArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "image", "profile"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());
        CommunityArticle article = communityArticleService
                .createArticle(member.getId(), study.getId(), new ArticleRequest("제목", "내용"));

        // act
        final ResponseEntity<Void> response = sut.updateArticle(member.getId(), study.getId(), article.getId(),
                new ArticleRequest("제목 수정", "내용 수정"));

        // assert
        CommunityArticle actualArticle = communityArticleRepository.findById(article.getId()).orElseThrow();
        CommunityArticle expectArticle = new CommunityArticle(article.getId(), "제목 수정", "내용 수정", member.getId(), study);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actualArticle).isEqualTo(expectArticle);
    }

    @DisplayName("스터디가 없는 경우 게시글 수정 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenUpdateToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());
        CommunityArticle article = communityArticleService
                .createArticle(member.getId(), study.getId(), new ArticleRequest("제목", "내용"));
        long notFoundStudyId = study.getId() + 1L;

        // act & assert
        assertThatThrownBy(() -> sut
                .updateArticle(member.getId(), notFoundStudyId, article.getId(), new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("게시글이 없는 경우 수정 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenUpdateToNotFoundArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());

        // act & assert
        assertThatThrownBy(
                () -> sut.updateArticle(member.getId(), study.getId(), 1L, new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 사용자가 스터디 커뮤니티 게시글을 수정한 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenUpdateByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getId(), study.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut
                .updateArticle(other.getId(), study.getId(), article.getId(), new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("스터디와 연관되지 않은 게시글 수정 시 예외 발생")
    @Test
    void throwExceptionWhenUpdateNotRelatedArticleWithStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        Study hasArticleStudy = studyService.createStudy(member.getGithubId(),
                javaStudyBuilder.startDate(LocalDate.now()).build());
        Study notHasArticleStudy = studyService.createStudy(member.getGithubId(),
                javaStudyBuilder.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getId(), hasArticleStudy.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut.updateArticle(member.getId(), notHasArticleStudy.getId(), article.getId(),
                new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("작성자 외에 게시글 수정 시 예외 발생")
    @Test
    void throwExceptionWhenUpdateNotAuthor() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "other", "imageUrl", "profileUrl"));

        Study study = studyService.createStudy(member.getGithubId(),
                javaStudyBuilder.startDate(LocalDate.now()).build());
        studyService.participateStudy(other.getGithubId(), study.getId());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final CommunityArticle article = communityArticleService.createArticle(member.getId(), study.getId(),
                request);

        // act & assert
        assertThatThrownBy(() -> sut
                .updateArticle(other.getId(), study.getId(), article.getId(), new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(UneditableArticleException.class);
    }
}
