package com.woowacourse.moamoa.studyroom.service;

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
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class AbstractArticleServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private ArticleRepository<LinkArticle> articleRepository;

    @Autowired
    private LinkArticleDao linkArticleDao;

    @Autowired
    private EntityManager entityManager;

    private StudyService studyService;

    private AbstractArticleService<LinkArticle, LinkContent> sut;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        sut = new LinkArticleService(studyRoomRepository, articleRepository, linkArticleDao);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 링크 공유를 할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final ArticleRequest<LinkContent> articleRequest = new LinkArticleRequest("www.naver.com", "설명");

        assertThatThrownBy(() -> sut.createArticle(디우.getId(), 자바_스터디.getId(), articleRequest))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final ArticleRequest<LinkContent> articleRequest = new LinkArticleRequest("www.naver.com", "수정");

        assertThatThrownBy(() -> sut.updateArticle(짱구.getId(), 자바_스터디.getId(), -1L, articleRequest))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final ArticleRequest<LinkContent> articleRequest = new LinkArticleRequest("www.naver.com", "설명");
        final LinkArticle 링크_게시글 = createArticle(짱구, 자바_스터디, articleRequest);

        assertThatThrownBy(() -> sut.updateArticle(디우.getId(), 자바_스터디.getId(), 링크_게시글.getId(), articleRequest))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByInvalidLinkId() {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        assertThatThrownBy(() -> sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), -1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final ArticleRequest<LinkContent> articleRequest = new LinkArticleRequest("www.naver.com", "설명");
        final LinkArticle 링크_게시글 = sut.createArticle(짱구.getId(), 자바_스터디.getId(), articleRequest);

        assertThatThrownBy(() -> sut.deleteArticle(디우.getId(), 자바_스터디.getId(), 링크_게시글.getId()))
                .isInstanceOf(UneditableArticleException.class);
    }

    private Member saveMember(final Member member) {
        final Member savedMember = memberRepository.save(member);
        entityManager.flush();
        entityManager.clear();
        return savedMember;
    }

    private Study createStudy(final Member owner, StudyRequest studyRequest) {
        final Study study = studyService.createStudy(owner.getId(), studyRequest);
        entityManager.flush();
        entityManager.clear();
        return study;
    }

    private LinkArticle createArticle(final Member author, final Study study,
                                      final ArticleRequest<LinkContent> articleRequest) {
        final LinkArticle article = sut.createArticle(author.getId(), study.getId(), articleRequest);
        entityManager.flush();
        entityManager.clear();
        return article;
    }
}
