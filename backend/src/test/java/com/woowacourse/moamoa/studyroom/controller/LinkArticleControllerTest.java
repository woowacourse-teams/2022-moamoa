package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.LinkArticleService;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryTest
class LinkArticleControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private LinkArticleRepository linkArticleRepository;

    @Autowired
    private JpaRepository<LinkArticle, Long> articleRepository;

    @Autowired
    private LinkArticleDao linkArticleDao;

    @Autowired
    private EntityManager entityManager;

    private LinkArticleController sut;

    private StudyService studyService;
    private LinkArticleService linkArticleService;
    private ArticleService<LinkArticle, LinkContent> articleService;

    @BeforeEach
    void setUp() {
        linkArticleService = new LinkArticleService(linkArticleDao);
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        articleService = new ArticleService<>(studyRoomRepository, articleRepository);
        sut = new LinkArticleController(linkArticleService, articleService);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 링크 공유를 할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final Member 짱구 = memberRepository.save(짱구());
        final Member 디우 = memberRepository.save(디우());
        final Study 자바_스터디 = studyService.createStudy(짱구.getId(), 자바_스터디_신청서(LocalDate.now()));

        entityManager.flush();
        entityManager.clear();

        final LinkArticleRequest articleRequest =
                new LinkArticleRequest("https://github.com/sc0116", "링크 설명입니다.");

        assertThatThrownBy(() -> sut.createLink(디우.getId(), 자바_스터디.getId(), articleRequest))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final Member 짱구 = memberRepository.save(짱구());
        final Study 자바_스터디 = studyService.createStudy(짱구.getId(), 자바_스터디_신청서(LocalDate.now()));

        entityManager.flush();
        entityManager.clear();

        final LinkArticleRequest editingLinkRequest = new LinkArticleRequest("www.naver.com", "수정");

        assertThatThrownBy(() -> sut.updateLink(짱구.getId(), 자바_스터디.getId(), -1L, editingLinkRequest))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final Member 짱구 = memberRepository.save(짱구());
        final Member 디우 = memberRepository.save(디우());

        final Study 자바_스터디 = studyService.createStudy(짱구.getId(), 자바_스터디_신청서(LocalDate.now()));

        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116", "링크 설명입니다.");
        final LinkArticle 링크_게시글 = articleService.createArticle(짱구.getId(), 자바_스터디.getId(), articleRequest);

        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() -> sut.updateLink(디우.getId(), 자바_스터디.getId(), 링크_게시글.getId(), articleRequest))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByInvalidLinkId() {
        final Member 짱구 = memberRepository.save(짱구());
        final Study 자바_스터디 = studyService.createStudy(짱구.getId(), 자바_스터디_신청서(LocalDate.now()));

        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() -> sut.deleteLink(짱구.getId(), 자바_스터디.getId(), -1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotParticipatedMember() {
        final Member 짱구 = memberRepository.save(짱구());
        final Member 디우 = memberRepository.save(디우());

        final Study 자바_스터디 = studyService.createStudy(짱구.getId(), 자바_스터디_신청서(LocalDate.now()));

        final LinkArticleRequest articleRequest = new LinkArticleRequest("https://github.com/sc0116", "링크 설명입니다.");
        final LinkArticle 링크_게시글 = articleService.createArticle(짱구.getId(), 자바_스터디.getId(), articleRequest);

        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() -> sut.deleteLink(디우.getId(), 자바_스터디.getId(), 링크_게시글.getId()))
                .isInstanceOf(UneditableArticleException.class);
    }
}
