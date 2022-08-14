package com.woowacourse.moamoa.studyroom.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
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
public class ArticleControllerTest {

    CreatingStudyRequestBuilder javaStudyRequest = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepositoryFactory articleRepositoryFactory;

    @Autowired
    private ArticleDao articleDao;

    private StudyService studyService;
    private ArticleController sut;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new ArticleController(new ArticleService(studyRepository,
                articleRepositoryFactory, articleDao));
    }

    @DisplayName("커뮤니티 게시글을 작성한다.")
    @Test
    void createCommunityArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(member.getId(), study.getId(),
                ArticleType.COMMUNITY, request
        );

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/community/articles/", ""));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/community/articles/\\d+");
        assertThat(articleRepositoryFactory.getRepository(ArticleType.COMMUNITY).findById(articleId).get())
                .isEqualTo(new CommunityArticle(articleId, "게시글 제목", "게시글 내용",
                        member.getId(), study, ArticleType.COMMUNITY));
    }

    @DisplayName("커뮤니티 공지사항을 작성한다.")
    @Test
    void createNoticeArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        ArticleRequest request = new ArticleRequest("게시글 제목", "게시글 내용");

        // act
        ResponseEntity<Void> response = sut.createArticle(member.getId(), study.getId(), ArticleType.NOTICE, request);

        // assert
        String location = response.getHeaders().getLocation().getPath();
        Long articleId = Long.valueOf(location.replaceAll("/api/studies/\\d+/notice/articles/", ""));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(location).matches("/api/studies/\\d+/notice/articles/\\d+");
        assertThat(articleRepositoryFactory.getRepository(ArticleType.NOTICE).findById(articleId).get())
                .isEqualTo(new NoticeArticle(articleId, "게시글 제목", "게시글 내용",
                        member.getId(), study, ArticleType.NOTICE));
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(member.getId(), 1L, ArticleType.COMMUNITY,
                new ArticleRequest("제목", "내용")
        ))
                .isInstanceOf(StudyNotFoundException.class);
    }
}
