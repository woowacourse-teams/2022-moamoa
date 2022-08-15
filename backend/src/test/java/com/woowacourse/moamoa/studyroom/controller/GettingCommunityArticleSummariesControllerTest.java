package com.woowacourse.moamoa.studyroom.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.query.PostArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.UnviewableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.PostArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class GettingCommunityArticleSummariesControllerTest {

    CreatingStudyRequestBuilder javaStudyRequest = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    private ArticleService articleService;

    private StudyService studyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ArticleRepositoryFactory articleRepositoryFactory;

    @Autowired
    private PostArticleDao postArticleDao;

    private ArticleController sut;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(studyRoomRepository,
                articleRepositoryFactory, postArticleDao);
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new ArticleController(articleService);
    }

    @DisplayName("스터디 커뮤니티 글 목록을 조회한다.")
    @Test
    void getCommunityArticles() {
        // arrange
        Member 그린론 = memberRepository.save(new Member(1L, "그린론", "http://image", "http://profile"));

        Study study = studyService.createStudy(그린론.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        articleService
                .createArticle(그린론.getId(), study.getId(), new PostArticleRequest("제목1", "내용1"), ArticleType.COMMUNITY);
        articleService
                .createArticle(그린론.getId(), study.getId(), new PostArticleRequest("제목2", "내용2"), ArticleType.COMMUNITY);
        Article article3 = articleService
                .createArticle(그린론.getId(), study.getId(), new PostArticleRequest("제목3", "내용3"), ArticleType.COMMUNITY);
        Article article4 = articleService
                .createArticle(그린론.getId(), study.getId(), new PostArticleRequest("제목4", "내용4"), ArticleType.COMMUNITY);
        Article article5 = articleService
                .createArticle(그린론.getId(), study.getId(), new PostArticleRequest("제목5", "내용5"), ArticleType.COMMUNITY);

        // act
        ResponseEntity<ArticleSummariesResponse> response = sut.getArticles(그린론.getId(), study.getId(),
                ArticleType.COMMUNITY, PageRequest.of(0, 3));

        // assert
        AuthorResponse author = new AuthorResponse(1L, "그린론", "http://image", "http://profile");

        List<ArticleSummaryResponse> articles = List.of(
                new ArticleSummaryResponse(article5.getId(), author, "제목5", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(article4.getId(), author, "제목4", LocalDate.now(), LocalDate.now()),
                new ArticleSummaryResponse(article3.getId(), author, "제목3", LocalDate.now(), LocalDate.now())
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                new ArticleSummariesResponse(articles, 0, 1, 5)
        );
    }

    @DisplayName("스터디가 없는 경우 게시글 목록 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        // act & assert
        assertThatThrownBy(() -> sut.getArticles(member.getId(), 1L, ArticleType.COMMUNITY, PageRequest.of(0, 3)))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 사용자가 스터디 커뮤니티 게시글 목록을 조회한 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        // act & assert
        assertThatThrownBy(
                () -> sut.getArticles(other.getId(), study.getId(), ArticleType.COMMUNITY, PageRequest.of(0, 3)))
                .isInstanceOf(UnviewableArticleException.class);
    }
}
