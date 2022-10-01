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
import com.woowacourse.moamoa.studyroom.domain.article.NoticeArticle;
import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import com.woowacourse.moamoa.studyroom.domain.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.domain.repository.article.NoticeArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.NoticeArticleDao;
import com.woowacourse.moamoa.studyroom.service.request.NoticeArticleRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class NoticeArticleServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private NoticeArticleRepository articleRepository;

    @Autowired
    private NoticeArticleDao articleDao;

    private NoticeArticleService sut;

    @BeforeEach
    void setUp() {
        sut = new NoticeArticleService(studyRoomRepository, articleRepository, articleDao);
    }

    @DisplayName("게시글을 작성한다.")
    @Test
    void createArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final NoticeContent noticeContent = new NoticeContent("제목", "설명");

        // act
        final NoticeArticle article = sut.createArticle(짱구.getId(), 자바_스터디.getId(), noticeContent);

        // assert
        NoticeArticle actualArticle = articleRepository.findById(article.getId())
                .orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(noticeContent);
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenWriteToNotFoundStudy() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final NoticeContent noticeContent = new NoticeContent("제목", "설명");

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(짱구.getId(), 1L, noticeContent))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 게시글을 작성할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final NoticeContent noticeContent = new NoticeContent("제목", "설명");

        assertThatThrownBy(() -> sut.createArticle(디우.getId(), 자바_스터디.getId(), noticeContent))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updateArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final NoticeContent noticeContent = new NoticeContent("제목", "설명");
        final NoticeArticle article = sut.createArticle(짱구.getId(), 자바_스터디.getId(), noticeContent);

        final NoticeContent newContent = new NoticeContent("제목 수정", "설명 수정");

        // act
        sut.updateArticle(짱구.getId(), 자바_스터디.getId(), article.getId(), newContent);

        // assert
        NoticeArticle actualArticle = articleRepository.findById(article.getId()).orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(newContent);
    }

    @DisplayName("존재하지 않는 게시글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final NoticeContent noticeContent = new NoticeContent("제목", "수정");

        assertThatThrownBy(() -> sut.updateArticle(짱구.getId(), 자바_스터디.getId(), -1L, noticeContent))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 게시글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final NoticeContent noticeContent = new NoticeContent("제목", "설명");
        final NoticeArticle 링크_게시글 = createArticle(짱구, 자바_스터디, noticeContent);

        assertThatThrownBy(() -> sut.updateArticle(디우.getId(), 자바_스터디.getId(), 링크_게시글.getId(),
                noticeContent))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    void deleteArticle() {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final NoticeContent noticeContent = new NoticeContent("제목", "설명");
        final NoticeArticle 게시글 = createArticle(짱구, 자바_스터디, noticeContent);

        //act
        sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), 게시글.getId());

        //assert
        entityManager.flush();
        entityManager.clear();

        assertThat(articleRepository.findById(게시글.getId())).isEmpty();
    }

    @DisplayName("존재하지 않는 게시글을 삭제할 수 없다.")
    @Test
    void deleteByInvalidLinkId() {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        assertThatThrownBy(() -> sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), -1L))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 게시글을 삭제할 수 없다.")
    @Test
    void deleteByNotParticipatedMember() {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final NoticeContent noticeContent = new NoticeContent("제목", "설명");
        final NoticeArticle 링크_게시글 = sut.createArticle(짱구.getId(), 자바_스터디.getId(), noticeContent);

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
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        final Study study = studyService.createStudy(owner.getId(), studyRequest);
        entityManager.flush();
        entityManager.clear();
        return study;
    }

    private NoticeArticle createArticle(final Member author, final Study study,
                                      final NoticeContent noticeContent) {
        final NoticeArticle article = sut.createArticle(author.getId(), study.getId(), noticeContent);
        entityManager.flush();
        entityManager.clear();
        return article;
    }
}
