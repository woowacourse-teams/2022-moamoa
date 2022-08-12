package com.woowacourse.moamoa.community.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.community.domain.Article;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.domain.repository.NoticeArticleRepository;
import com.woowacourse.moamoa.community.query.ArticleDao;
import com.woowacourse.moamoa.community.service.ArticleRepositoryFactory;
import com.woowacourse.moamoa.community.service.ArticleService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class UpdatingArticleControllerTest {

    CreatingStudyRequestBuilder javaStudyBuilder = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeArticleRepository noticeArticleRepository;

    @Autowired
    private CommunityArticleRepository communityArticleRepository;

    @Autowired
    private ArticleDao articleDao;

    private StudyService studyService;
    private ArticleController sut;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        articleService = new ArticleService(memberRepository, studyRepository,
                articleDao,
                new ArticleRepositoryFactory(communityArticleRepository, noticeArticleRepository));
        sut = new ArticleController(articleService);
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updateArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "image", "profile"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());
        Article article = articleService
                .createArticle(member.getId(), study.getId(), new ArticleRequest("제목", "내용"), "community");

        // act
        final ResponseEntity<Void> response = sut.updateArticle(member.getId(), study.getId(), "community", article.getId(),
                new ArticleRequest("제목 수정", "내용 수정"));

        // assert
        Article actualArticle = communityArticleRepository.findById(article.getId()).orElseThrow();
        CommunityArticle expectArticle = new CommunityArticle(article.getId(), "제목 수정", "내용 수정", member.getId(), study);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actualArticle).isEqualTo(expectArticle);
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
                () -> sut.updateArticle(member.getId(), study.getId(), "community",
                        1L, new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("게시글을 수정할 수 없는 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenUpdateByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");
        final Article article = articleService.createArticle(member.getId(), study.getId(),
                request, "community");

        // act & assert
        assertThatThrownBy(() -> sut
                .updateArticle(other.getId(), study.getId(), "community",
                        article.getId(), new ArticleRequest("제목 수정", "내용 수정")))
                .isInstanceOf(UneditableArticleException.class);
    }
}
