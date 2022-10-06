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
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.ArticleDao;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ArticleServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleDao articleDao;

    private ArticleService sut;

    @BeforeEach
    void setUp() {
        sut = new ArticleService(studyRoomRepository, articleRepository, articleDao);
    }

    @DisplayName("게시글을 작성한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void createArticle(ArticleType type) {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final Content content = new Content("제목", "설명");

        // act
        final Long articleId = sut.createArticle(짱구.getId(), 자바_스터디.getId(), content, type);

        // assert
        Article actualArticle = articleRepository.findById(articleId)
                .orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(content);
    }

    @DisplayName("스터디가 없는 경우 게시글 작성 시 예외가 발생한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void throwExceptionWhenWriteToNotFoundStudy(ArticleType type) {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Content content = new Content("제목", "설명");

        // act & assert
        assertThatThrownBy(() -> sut.createArticle(짱구.getId(), 1L, content, type))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 회원은 게시글을 작성할 수 없다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void createByNotParticipatedMember(ArticleType type) {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final Content content = new Content("제목", "설명");

        assertThatThrownBy(() -> sut.createArticle(디우.getId(), 자바_스터디.getId(), content, type))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("게시글을 수정한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateArticle(ArticleType type) {
        // arrange
        final Member 방장 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        final Long articleId = createArticle(방장, 자바_스터디, new Content("제목", "설명"));

        final Content newContent = new Content("제목 수정", "설명 수정");

        // act
        sut.updateArticle(방장.getId(), 자바_스터디.getId(), articleId, newContent, type);

        // assert
        Article actualArticle = articleRepository.findById(articleId).orElseThrow();
        assertThat(actualArticle.getContent()).isEqualTo(new Content("제목 수정", "설명 수정"));
    }

    @DisplayName("존재하지 않는 게시글을 수정할 수 없다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateByInvalidLinkId(ArticleType type) {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final Content content = new Content("제목", "수정");

        assertThatThrownBy(() -> sut.updateArticle(짱구.getId(), 자바_스터디.getId(), -1L, content, type))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 게시글을 수정할 수 없다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateByNotParticipatedMember(ArticleType type) {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final Content content = new Content("제목", "설명");
        final Long 링크_ID = createArticle(짱구, 자바_스터디, content);

        assertThatThrownBy(() -> sut.updateArticle(디우.getId(), 자바_스터디.getId(), 링크_ID, content, type))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("게시글을 삭제한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteArticle(ArticleType type) {
        // arrange
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));
        final Content content = new Content("제목", "설명");
        final Long 게시글_ID = createArticle(짱구, 자바_스터디, content);

        //act
        sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), 게시글_ID, type);

        //assert
        entityManager.flush();
        entityManager.clear();

        assertThat(articleRepository.findById(게시글_ID)).isEmpty();
    }

    @DisplayName("존재하지 않는 게시글을 삭제할 수 없다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteByInvalidLinkId(ArticleType type) {
        final Member 짱구 = saveMember(짱구());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        assertThatThrownBy(() -> sut.deleteArticle(짱구.getId(), 자바_스터디.getId(), -1L, type))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 게시글을 삭제할 수 없다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteByNotParticipatedMember(ArticleType type) {
        final Member 짱구 = saveMember(짱구());
        final Member 디우 = saveMember(디우());
        final Study 자바_스터디 = createStudy(짱구, 자바_스터디_신청서(LocalDate.now()));

        final Content content = new Content("제목", "설명");
        final Long 게시글_ID = sut.createArticle(짱구.getId(), 자바_스터디.getId(), content, type
        );

        assertThatThrownBy(() -> sut.deleteArticle(디우.getId(), 자바_스터디.getId(), 게시글_ID, ArticleType.COMMUNITY))
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

    private Long createArticle(final Member author, final Study study,
                                  final Content content) {
        final Long articleId = sut.createArticle(author.getId(), study.getId(), content,
                ArticleType.COMMUNITY
        );
        entityManager.flush();
        entityManager.clear();
        return articleId;
    }
}
