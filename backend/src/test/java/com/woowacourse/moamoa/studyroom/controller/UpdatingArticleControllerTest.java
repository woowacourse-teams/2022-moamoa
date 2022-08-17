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
import com.woowacourse.moamoa.study.service.request.StudyRequestBuilder;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.CommunityArticle;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
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
class UpdatingArticleControllerTest {

    StudyRequestBuilder javaStudyBuilder = new StudyRequestBuilder()
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
    private ArticleDao articleDao;

    private StudyService studyService;
    private ArticleController sut;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        articleService = new ArticleService(studyRoomRepository,
                articleRepositoryFactory, articleDao);
        sut = new ArticleController(articleService);
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updateArticle() {
        // arrange
        Member owner = memberRepository.save(new Member(1L, "username", "image", "profile"));
        Study study = studyService
                .createStudy(owner.getGithubId(), javaStudyBuilder.startDate(LocalDate.now()).build());
        Article article = articleService
                .createArticle(owner.getId(), study.getId(), new ArticleRequest("제목", "내용"), COMMUNITY);

        // act
        final ResponseEntity<Void> response = sut
                .updateArticle(owner.getId(), study.getId(), article.getId(), COMMUNITY,
                        new ArticleRequest("제목 수정", "내용 수정"));

        // assert
        ArticleRepository<Article> articleRepository = articleRepositoryFactory.getRepository(COMMUNITY);
        Article actualArticle = articleRepository.findById(article.getId()).orElseThrow();

        StudyRoom expectStudyRoom = new StudyRoom(study.getId(), owner.getId(), Set.of());
        CommunityArticle expectArticle = new CommunityArticle(article.getId(), "제목 수정", "내용 수정", owner.getId(),
                expectStudyRoom);

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

        final Long memberId = member.getId();
        final Long studyId = study.getId();
        final ArticleRequest articleRequest = new ArticleRequest("제목 수정", "내용 수정");

        // act & assert
        assertThatThrownBy(() ->
                sut.updateArticle(memberId, studyId, 1L, COMMUNITY, articleRequest)
        ).isInstanceOf(ArticleNotFoundException.class);
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
        final Article article = articleService
                .createArticle(member.getId(), study.getId(), request, COMMUNITY);

        final Long otherId = other.getId();
        final Long studyId = study.getId();
        final Long articleId = article.getId();
        final ArticleRequest articleRequest = new ArticleRequest("제목 수정", "내용 수정");

        // act & assert
        assertThatThrownBy(() ->
                sut.updateArticle(otherId, studyId, articleId, COMMUNITY,
                        articleRequest
        )).isInstanceOf(UneditableArticleException.class);
    }
}
