package com.woowacourse.moamoa.studyroom.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.NoticeArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.service.NoticeArticleService;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
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
class GettingCommunityArticleSummariesControllerTest {

    StudyRequestBuilder javaStudyRequest = new StudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    private NoticeArticleService noticeArticleService;

    private StudyService studyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private NoticeArticleRepository noticeArticleRepository;

    @Autowired
    private NoticeArticleDao noticeArticleDao;

    private NoticeArticleController sut;

    @BeforeEach
    void setUp() {
        noticeArticleService = new NoticeArticleService(studyRoomRepository,
                noticeArticleRepository, noticeArticleDao);
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new NoticeArticleController(noticeArticleService);
    }

    @DisplayName("스터디 커뮤니티 글 목록을 조회한다.")
    @Test
    void getCommunityArticles() {
        // arrange
        Member 그린론 = memberRepository.save(new Member(1L, "그린론", "http://image", "http://profile"));

        Study study = studyService.createStudy(그린론.getId(), javaStudyRequest.startDate(LocalDate.now()).build());

        noticeArticleService
                .createArticle(그린론.getId(), study.getId(), new ArticleRequest("제목1", "내용1"));
        noticeArticleService
                .createArticle(그린론.getId(), study.getId(), new ArticleRequest("제목2", "내용2"));
        NoticeArticle article3 = noticeArticleService
                .createArticle(그린론.getId(), study.getId(), new ArticleRequest("제목3", "내용3"));
        NoticeArticle article4 = noticeArticleService
                .createArticle(그린론.getId(), study.getId(), new ArticleRequest("제목4", "내용4"));
        NoticeArticle article5 = noticeArticleService
                .createArticle(그린론.getId(), study.getId(), new ArticleRequest("제목5", "내용5"));

        // act
        ResponseEntity<ArticleSummariesResponse> response = sut.getArticles(study.getId(), PageRequest.of(0, 3));

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

    @DisplayName("스터디가 없는 경우 빈 리스트를 반환한다.")
    @Test
    void returnEmptyDataWhenWriteToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        final Long memberId = member.getId();
        final PageRequest pageRequest = PageRequest.of(0, 3);

        // act
        final ResponseEntity<ArticleSummariesResponse> response = sut.getArticles(1L, pageRequest);

        // assert
        assertThat(response.getBody().getArticles()).isEmpty();
    }
}
