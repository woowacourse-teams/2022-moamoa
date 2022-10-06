package com.woowacourse.moamoa.studyroom.service;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
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
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.link.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.link.LinkContent;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.link.repository.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class LinkArticleServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LinkArticleRepository articleRepository;

    @Autowired
    private LinkArticleDao linkArticleDao;

    private LinkArticleService sut;

    @BeforeEach
    void setUp() {
        sut = new LinkArticleService(studyRoomRepository, articleRepository, linkArticleDao);
    }

    @DisplayName("게시글을 작성한다.")
    @Test
    void createArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");

        // act
        final Long articleId = sut.createArticle(짱구.getId(), 자바_스터디.getId(), linkContent);

        // assert
        LinkArticle actualArticle = articleRepository.findById(articleId).orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(linkContent);
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(짱구.getId(), 1L, linkContent))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 게시글을 작성할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");

        assertThatThrownBy(() -> sut.createArticle(디우.getId(), 자바_스터디.getId(), linkContent))
                .isInstanceOf(UnwritableException.class);
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updateArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");
        final Long articleId = sut.createArticle(짱구.getId(), 자바_스터디.getId(), linkContent);

        final LinkContent newContent = new LinkContent("링크 수정", "설명 수정");

        // act
        sut.updateArticle(짱구.getId(), 자바_스터디.getId(), articleId, newContent);

        // assert
        LinkArticle actualArticle = articleRepository.findById(articleId).orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(new LinkContent("링크 수정", "설명 수정"));
    }

    @DisplayName("존재하지 않는 게시글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");

        assertThatThrownBy(() -> sut.updateArticle(짱구.getId(), 자바_스터디.getId(), -1L, linkContent))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 게시글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");

        final Long 게시글_ID = createArticle(짱구, 자바_스터디, linkContent);

        assertThatThrownBy(() -> sut.updateArticle(디우.getId(), 자바_스터디.getId(), 게시글_ID,
                linkContent))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    void deleteArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");
        final Long 게시글_ID = createArticle(짱구, 자바_스터디, linkContent);

        //act
        sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), 게시글_ID);

        //assert
        entityManager.flush();
        entityManager.clear();

        assertThat(articleRepository.findById(게시글_ID)).isEmpty();
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
        final LinkContent linkContent = new LinkContent("www.naver.com", "설명");
        final Long 게시글_ID = sut.createArticle(짱구.getId(), 자바_스터디.getId(), linkContent);

        assertThatThrownBy(() -> sut.deleteArticle(디우.getId(), 자바_스터디.getId(), 게시글_ID))
                .isInstanceOf(UneditableException.class);
    }

    private Member saveMember(final Member member) {
        final Member savedMember = memberRepository.save(member);
        entityManager.flush();
        entityManager.clear();
        return savedMember;
    }

    private Study createStudy(final Member owner, StudyRequest studyRequest) {
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        final Study study = studyService.createStudy(owner.getId(), studyRequest);
        entityManager.flush();
        entityManager.clear();
        return study;
    }

    private Long createArticle(final Member author, final Study study, final LinkContent content) {
        final Long articleId = sut.createArticle(author.getId(), study.getId(), content);
        entityManager.flush();
        entityManager.clear();
        return articleId;
    }
}
