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
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.request.CommunityArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.AuthorResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class GettingNoticeArticleControllerTest {

    StudyRequestBuilder javaStudyRequest = new StudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeArticleRepository noticeArticleRepository;

    @Autowired
    private NoticeArticleDao noticeArticleDao;

    private StudyService studyService;
    private NoticeArticleController sut;
    private NoticeArticleService noticeArticleService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        noticeArticleService = new NoticeArticleService(studyRoomRepository, noticeArticleRepository, noticeArticleDao);
        sut = new NoticeArticleController(noticeArticleService);
    }

    @DisplayName("스터디 게시글을 단건 조회한다.")
    @Test
    void getStudyCommunityArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getId(), javaStudyRequest.startDate(LocalDate.now()).build());

        CommunityArticleRequest request = new CommunityArticleRequest("게시글 제목", "게시글 내용");
        NoticeArticle article = noticeArticleService.createArticle(member.getId(), study.getId(), request);

        //act
        final ResponseEntity<ArticleResponse> response = sut.getArticle(study.getId(),
                article.getId());

        //assert
        final AuthorResponse expectedAuthorResponse = new AuthorResponse(
                member.getGithubId(), member.getUsername(), member.getImageUrl(), member.getProfileUrl()
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                new ArticleResponse(article.getId(), expectedAuthorResponse, request.getTitle(), request.getContent(),
                        LocalDate.now(), LocalDate.now()));
    }

    @DisplayName("게시글이 없는 경우 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingToNotFoundArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getId(), javaStudyRequest.startDate(LocalDate.now()).build());

        final Long memberId = member.getId();
        final Long studyId = study.getId();

        // act & assert
        assertThatThrownBy(() -> sut.getArticle(studyId, 1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }
}
