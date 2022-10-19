package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
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
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.TempArticleDao;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.TempArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UnViewableException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.CreatedArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.TempArticlesResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
public class TempArticleControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    @Autowired
    private TempArticleDao tempArticleDao;

    @Autowired
    private EntityManager entityManager;

    private TempArticleController sut;

    @BeforeEach
    void setUp() {
        sut = new TempArticleController(
                new TempArticleService(studyRoomRepository, articleRepository, tempArticleRepository, tempArticleDao)
        );
    }

    @DisplayName("정상적인 임시글 작성")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void writeTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act
        ResponseEntity<CreatedTempArticleIdResponse> response =
                sut.createTempArticle(방장.getId(), 자바_스터디.getId(), new ArticleRequest("제목", "내용"), type);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        long draftArticleId = response.getBody().getDraftArticleId();
        assertThat(tempArticleRepository.findById(draftArticleId)).isPresent();
    }

    @DisplayName("존재하지 않는 스터디에 임시글 작성 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void writeTempArticleToNotFoundStudy(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Long notFoundStudyId = 1L;

        // act & assert
        assertThatThrownBy(() -> sut.createTempArticle(방장.getId(), notFoundStudyId, new ArticleRequest("제목", "내용"),
                type))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("허용되지 않은 사용자가 임시글 작성 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void writeTempArticleByInvalidAccount(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());

        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act & assert
        assertThatThrownBy(() -> createTempArticle(비허가_사용자, 자바_스터디, new ArticleRequest("제목", "내용"), type))
                .isInstanceOf(UnwritableException.class);
    }

    @DisplayName("작성한 임시글 조회")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void getTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act
        ResponseEntity<TempArticleResponse> response = sut.getTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID,
                type);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        TempArticleResponse articleResponse = response.getBody();
        assertThat(articleResponse.getTitle()).isEqualTo("제목");
        assertThat(articleResponse.getContent()).isEqualTo("내용");
        assertThat(articleResponse.getCreatedDate()).isNotNull();
        assertThat(articleResponse.getLastModifiedDate()).isNotNull();
    }

    @DisplayName("존재하지 않는 임시글 조회 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void getTempArticleByNotFoundArticleId(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);
        Long 존재하지_않는_게시글_ID = 게시글_ID + 1;

        // act & assert
        assertThatThrownBy(() -> sut.getTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID, type))
                .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("작성자 외에 사용자가 임시글 조회 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void getTempArticleByInvalidAccount(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act & assert
        assertThatThrownBy(() -> sut.getTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID, type))
                .isInstanceOf(UnViewableException.class);
    }

    @DisplayName("작성자는 임시글을 삭제할 수 있다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act
        sut.deleteTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID, type);

        // assert
        assertThat(tempArticleRepository.findById(게시글_ID)).isEmpty();
    }

    @DisplayName("존재하지 않는 임시글을 삭제 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteNotFoundTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 존재하지_않는_게시글_ID = 1L;

        // act & assert
        assertThatThrownBy(() -> sut.deleteTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID, type))
                .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("작성자 외에 임시글을 삭제 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void deleteTempArticleByInvalidAccount(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act & assert
        assertThatThrownBy(() -> sut.deleteTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID, type))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("작성자는 임시글을 수정할 수 있다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act
        sut.updateTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID, new ArticleRequest("수정된 제목", "수정된 내용"),
                type);

        // assert
        final TempArticleResponse tempArticle = getTempArticle(방장, 자바_스터디, 게시글_ID, type);
        assertThat(tempArticle.getTitle()).isEqualTo("수정된 제목");
        assertThat(tempArticle.getContent()).isEqualTo("수정된 내용");
    }

    @DisplayName("존재하지 않는 임시글 수정 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateNotFoundTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 존재하지_않는_게시글_ID = 1L;

        // act & assert
        final ArticleRequest request = new ArticleRequest("수정된 제목", "수정된 내용");
        assertThatThrownBy(() ->
                sut.updateTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID, request, type)
        )
                .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("작성자 외의 사용자가 임시글 수정 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void updateByInvalidAccount(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act & assert
        final ArticleRequest request = new ArticleRequest("수정된 제목", "수정된 내용");
        assertThatThrownBy(() -> sut.updateTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID, request,
                type))
                .isInstanceOf(UneditableException.class);
    }

    @DisplayName("작성한 임시글이 없는 경우 빈 목록을 조회한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void getEmptyTempArticles(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act
        final ResponseEntity<TempArticlesResponse> responses = sut
                .getTempArticles(방장.getId(), Pageable.ofSize(5), type);

        // assert
        assertThat(responses.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responses.getBody()).isNotNull();
        assertThat(responses.getBody().getDraftArticles()).isEmpty();
        assertThat(responses.getBody().getCurrentPage()).isEqualTo(0);
        assertThat(responses.getBody().getLastPage()).isEqualTo(0);
        assertThat(responses.getBody().getTotalCount()).isEqualTo(0);
    }

    @DisplayName("작성한 임시글을 공개한다.")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void publishTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act
        final ResponseEntity<CreatedArticleIdResponse> response = sut
                .publishTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID, type);

        // arrange
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        assertThat(tempArticleRepository.findById(게시글_ID)).isEmpty();
        final Long articleId = response.getBody().getArticleId();
        final Article article = articleRepository.findById(articleId).get();
        assertThat(article.getContent()).isEqualTo(new Content("제목", "내용"));
    }

    @DisplayName("존재하지 않는 임시글을 공개 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void publishNotFoundTempArticle(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 존재하지_않는_게시글_ID = 1L;

        // act & assert
        assertThatCode(() -> sut.publishTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID, type))
                .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("권한이 없는 사용자가 공개 시 예외 발생")
    @ParameterizedTest
    @EnumSource(ArticleType.class)
    void publishTempArticleByForbiddenAccessor(final ArticleType type) {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createTempArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"), type);

        // act & assert
        assertThatCode(() -> sut.publishTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID, type))
                .isInstanceOf(UnwritableException.class);
        assertThat(tempArticleRepository.findById(게시글_ID)).isPresent();
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

    private Long createTempArticle(
            final Member author, final Study study, final ArticleRequest request, final ArticleType type
    ) {
        ResponseEntity<CreatedTempArticleIdResponse> response = sut
                .createTempArticle(author.getId(), study.getId(), request, type);
        entityManager.flush();
        entityManager.clear();
        return response.getBody().getDraftArticleId();
    }

    private TempArticleResponse getTempArticle(
            final Member author, final Study study, final Long articleId, final ArticleType type
    ) {
        entityManager.flush();
        entityManager.clear();
        return sut.getTempArticle(author.getId(), study.getId(), articleId, type).getBody();
    }
}
