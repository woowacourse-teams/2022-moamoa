package com.woowacourse.moamoa.community.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.service.CommunityArticleService;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.community.service.response.AuthorResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
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

    private CommunityArticleService communityArticleService;

    private StudyService studyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CommunityArticleRepository communityArticleRepository;

    @Autowired
    private CommunityArticleDao communityArticleDao;

    @BeforeEach
    void setUp() {
        communityArticleService = new CommunityArticleService(memberRepository, studyRepository,
                communityArticleRepository, communityArticleDao);
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
    }

    @DisplayName("스터디 커뮤니티 글 목록을 조회한다.")
    @Test
    void getCommunityArticles() {
        // arrange
        Member 그린론 = memberRepository.save(new Member(1L, "그린론", "http://image", "http://profile"));

        Study study = studyService.createStudy(그린론.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        communityArticleService.createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("제목1", "내용1"));
        communityArticleService.createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("제목2", "내용2"));
        CommunityArticle article3 = communityArticleService
                .createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("제목3", "내용3"));
        CommunityArticle article4 = communityArticleService
                .createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("제목4", "내용4"));
        CommunityArticle article5 = communityArticleService
                .createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("제목5", "내용5"));

        CommunityArticleController sut = new CommunityArticleController(communityArticleService);

        // act
        ResponseEntity<ArticleSummariesResponse> response = sut.getArticles(그린론.getGithubId(), study.getId(), PageRequest.of(0, 3));

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
}
