package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.*;
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
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.TempArticleDao;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.TempArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UnviewableException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class TempArticleControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    @Autowired
    private TempArticleDao tempArticleDao;

    @Autowired
    private EntityManager entityManager;

    private TempArticleController sut;

    @BeforeEach
    void setUp() {
        sut = new TempArticleController(new TempArticleService(studyRoomRepository, tempArticleRepository, tempArticleDao));
    }

    @DisplayName("정상적인 임시글 작성")
    @Test
    void writeTempArticle() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act
        ResponseEntity<CreatedTempArticleIdResponse> response =
                sut.createTempArticle(방장.getId(), 자바_스터디.getId(), new ArticleRequest("제목", "내용"));

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        long draftArticleId = response.getBody().getDraftArticleId();
        assertThat(tempArticleRepository.findById(draftArticleId)).isPresent();
    }

    @DisplayName("존재하지 않는 스터디에 임시글 작성 시 예외 발생")
    @Test
    void writeTempArticleToNotFoundStudy() {
        // arrange
        Member 방장 = saveMember(짱구());
        Long notFoundStudyId = 1L;

        // act & assert
        assertThatThrownBy(() -> sut.createTempArticle(방장.getId(), notFoundStudyId, new ArticleRequest("제목", "내용")))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("허용되지 않은 사용자가 임시글 작성 시 예외 발생")
    @Test
    void writeTempArticleByInvalidAccount() {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());

        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act & assert
        assertThatThrownBy(() -> createDraftArticle(비허가_사용자, 자바_스터디, new ArticleRequest("제목", "내용")))
                .isInstanceOf(UnwritableException.class);
    }

    @DisplayName("작성한 임시글 조회")
    @Test
    void getTempArticle() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createDraftArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"));

        // act
        ResponseEntity<TempArticleResponse> response = sut.getTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID);

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
    @Test
    void getTempArticleByNotFoundArticleId() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createDraftArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"));
        Long 존재하지_않는_게시글_ID = 게시글_ID + 1;

        // act & assert
        assertThatThrownBy(() -> sut.getTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID))
            .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("작성자 외에 사용자가 임시글 조회 시 예외 발생")
    @Test
    void getTempArticleByInvalidAccount() {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createDraftArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"));

        // act & assert
        assertThatThrownBy(() -> sut.getTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID))
                .isInstanceOf(UnviewableException.class);
    }

    @DisplayName("작성자는 임시글을 삭제할 수 있다.")
    @Test
    void deleteTempArticle() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createDraftArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"));

        // act
        sut.deleteTempArticle(방장.getId(), 자바_스터디.getId(), 게시글_ID);

        // assert
        assertThat(tempArticleRepository.findById(게시글_ID)).isEmpty();
    }

    @DisplayName("존재하지 않는 임시글을 삭제 시 예외 발생")
    @Test
    void deleteNotFoundTempArticle() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 존재하지_않는_게시글_ID = 1L;

        // act & assert
        assertThatThrownBy(() -> sut.deleteTempArticle(방장.getId(), 자바_스터디.getId(), 존재하지_않는_게시글_ID))
            .isInstanceOf(TempArticleNotFoundException.class);
    }

    @DisplayName("작성자 외에 임시글을 삭제 시 예외 발생")
    @Test
    void deleteTempArticleByInvalidAccount() {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));
        Long 게시글_ID = createDraftArticle(방장, 자바_스터디, new ArticleRequest("제목", "내용"));

        // act & assert
        assertThatThrownBy(() -> sut.deleteTempArticle(비허가_사용자.getId(), 자바_스터디.getId(), 게시글_ID))
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

    private Long createDraftArticle(
            final Member author, final Study study, final ArticleRequest request
    ) {
        ResponseEntity<CreatedTempArticleIdResponse> response = sut
                .createTempArticle(author.getId(), study.getId(), request);
        entityManager.flush();
        entityManager.clear();
        return response.getBody().getDraftArticleId();
    }
}
