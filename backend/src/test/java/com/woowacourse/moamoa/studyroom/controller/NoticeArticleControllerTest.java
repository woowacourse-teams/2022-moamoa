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
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import com.woowacourse.moamoa.studyroom.domain.repository.article.NoticeArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.service.AbstractArticleService;
import com.woowacourse.moamoa.studyroom.service.NoticeArticleService;
import com.woowacourse.moamoa.studyroom.service.request.NoticeArticleRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class NoticeArticleControllerTest {

    StudyRequestBuilder javaStudyRequest = new StudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeArticleRepository noticeArticleRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private NoticeArticleDao noticeArticleDao;

    @Autowired
    private JpaRepository<NoticeArticle, Long> articleRepository;

    private StudyService studyService;
    private NoticeArticleController sut;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new NoticeArticleController(
                new NoticeArticleService(studyRoomRepository, articleRepository, noticeArticleDao)
        );
    }

    @DisplayName("커뮤니티 게시글을 작성한다.")
    @Test
    void createCommunityArticle() {
        // arrange
        Member owner = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(owner.getId(), javaStudyRequest.startDate(LocalDate.now()).build());

        NoticeArticleRequest request = new NoticeArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(owner.getId(), study.getId(), request);

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/notice/articles/", ""));

        NoticeArticle actualArticle = noticeArticleRepository.findById(articleId)
                .orElseThrow();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/notice/articles/\\d+");
        assertThat(actualArticle.getContent()).isEqualTo(new NoticeContent("게시글 제목", "게시글 내용"));
    }

    @DisplayName("커뮤니티 공지사항을 작성한다.")
    @Test
    void createNoticeArticle() {
        // arrange
        Member owner = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(owner.getId(), javaStudyRequest.startDate(LocalDate.now()).build());

        NoticeArticleRequest request = new NoticeArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(owner.getId(), study.getId(), request);

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/notice/articles/", ""));
        NoticeArticle actualArticle = noticeArticleRepository.findById(articleId).orElseThrow();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/notice/articles/\\d+");
        assertThat(actualArticle.getContent()).isEqualTo(new NoticeContent("게시글 제목", "게시글 내용"));
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        final Long memberId = member.getId();
        final NoticeArticleRequest articleRequest = new NoticeArticleRequest("제목", "내용");

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(memberId, 1L, articleRequest))
                .isInstanceOf(StudyNotFoundException.class);
    }
}
