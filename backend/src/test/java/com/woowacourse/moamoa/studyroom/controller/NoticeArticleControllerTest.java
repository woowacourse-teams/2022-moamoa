package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;
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
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ArticleRepositoryFactory articleRepositoryFactory;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private ArticleDao articleDao;

    private StudyService studyService;
    private NoticeArticleController sut;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new NoticeArticleController(
                new ArticleService(studyRoomRepository, articleRepositoryFactory, articleDao));
    }

    @DisplayName("커뮤니티 게시글을 작성한다.")
    @Test
    void createCommunityArticle() {
        // arrange
        Member owner = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(owner.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(owner.getId(), study.getId(), request);

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/notice/articles/", ""));

        Article actualArticle = articleRepositoryFactory.getRepository(NOTICE).findById(articleId)
                .orElseThrow();
        StudyRoom expectStudyRoom = new StudyRoom(study.getId(), owner.getId(), Set.of());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/notice/articles/\\d+");
        assertThat(actualArticle).isEqualTo(
                new NoticeArticle(articleId, "게시글 제목", "게시글 내용", owner.getId(), expectStudyRoom)
        );
    }

    @DisplayName("커뮤니티 공지사항을 작성한다.")
    @Test
    void createNoticeArticle() {
        // arrange
        Member owner = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(owner.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(owner.getId(), study.getId(), request);

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/notice/articles/", ""));
        Article actualArticle = articleRepositoryFactory.getRepository(NOTICE).findById(articleId).orElseThrow();

        StudyRoom expectStudyRoom = new StudyRoom(study.getId(), owner.getId(), Set.of());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/notice/articles/\\d+");
        assertThat(actualArticle).isEqualTo(
                new NoticeArticle(articleId, "게시글 제목", "게시글 내용", owner.getId(), expectStudyRoom)
        );
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        final Long memberId = member.getId();
        final ArticleRequest articleRequest = new ArticleRequest("제목", "내용");

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(memberId, 1L, articleRequest))
                .isInstanceOf(StudyNotFoundException.class);
    }
}
