package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.studyroom.domain.ArticleType.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.query.PostArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.PostArticleRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class DeletingArticleControllerTest {

    CreatingStudyRequestBuilder javaStudyRequest = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepositoryFactory articleRepositoryFactory;

    @Autowired
    private PostArticleDao postArticleDao;

    private StudyService studyService;
    private ArticleController sut;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        articleService = new ArticleService(studyRoomRepository,
                articleRepositoryFactory, postArticleDao);
        sut = new ArticleController(articleService);
    }

    @DisplayName("스터디 커뮤니티 게시글을 삭제한다.")
    @Test
    void deleteCommunityArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        PostArticleRequest request = new PostArticleRequest("게시글 제목", "게시글 내용");
        Article article = articleService.createArticle(member.getId(), study.getId(), request, COMMUNITY);

        //act
        sut.deleteArticle(member.getId(), study.getId(), article.getId(), COMMUNITY);

        //assert
        ArticleRepository<Article> articleRepository = articleRepositoryFactory.getRepository(COMMUNITY);
        assertThat(articleRepository.existsById(article.getId())).isFalse();
    }

    @DisplayName("게시글이 없는 경우 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenGettingToNotFoundArticle() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(member.getId(), study.getId(), 1L, COMMUNITY))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("게시글을 삭제할 수 없는 경우 예외가 발생한다.")
    @Test
    void throwExceptionWhenDeletingByNotParticipant() {
        // arrange
        Member member = memberRepository.save(new Member(1L, "username", "imageUrl", "profileUrl"));
        Member other = memberRepository.save(new Member(2L, "username2", "imageUrl", "profileUrl"));

        Study study = studyService
                .createStudy(member.getGithubId(), javaStudyRequest.startDate(LocalDate.now()).build());

        PostArticleRequest request = new PostArticleRequest("게시글 제목", "게시글 내용");
        Article article = articleService.createArticle(member.getId(), study.getId(), request, COMMUNITY);

        // act & assert
        assertThatThrownBy(() -> sut.deleteArticle(other.getId(), study.getId(), article.getId(), COMMUNITY))
                .isInstanceOf(UneditableArticleException.class);
    }
}
