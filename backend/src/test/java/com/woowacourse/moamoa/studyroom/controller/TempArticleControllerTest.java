package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
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
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.UnwritableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
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
    private EntityManager entityManager;

    private TempArticleController sut;

    @BeforeEach
    void setUp() {
        sut = new TempArticleController(new TempArticleService(studyRoomRepository, tempArticleRepository));
    }

    @DisplayName("공지사항 임시글 작성")
    @Test
    void writeDraftNoticeArticle() {
        // arrange
        Member 방장 = saveMember(짱구());
        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act
        ResponseEntity<CreatedTempArticleIdResponse> response = createDraftArticle(방장, 자바_스터디,
                new ArticleRequest("제목", "내용"));

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();

        long draftArticleId = response.getBody().getDraftArticleId();
        assertThat(tempArticleRepository.findById(draftArticleId)).isPresent();
    }

    @DisplayName("존재하지 않는 스터디에 임시 공지글 작성 시 예외 발생")
    @Test
    void writeDraftNoticeToNotFoundStudy() {
        // arrange
        Member 방장 = saveMember(짱구());
        Long notFoundStudyId = 1L;

        // act & assert
        assertThatThrownBy(() -> sut.createTempArticle(방장.getId(), notFoundStudyId, new ArticleRequest("제목", "내용")))
                .isInstanceOf(StudyNotFoundException.class);
    }

    @DisplayName("허용되지 않은 사용자가 임시 공지사항 작성 시 예외 발생")
    @Test
    void writeDraftNoticeByInvalidAccount() {
        // arrange
        Member 방장 = saveMember(짱구());
        Member 비허가_사용자 = saveMember(베루스());

        Study 자바_스터디 = createStudy(방장, 자바_스터디_신청서(LocalDate.now()));

        // act & assert
        assertThatThrownBy(() -> createDraftArticle(비허가_사용자, 자바_스터디, new ArticleRequest("제목", "내용")))
                .isInstanceOf(UnwritableException.class);
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

    private ResponseEntity<CreatedTempArticleIdResponse> createDraftArticle(
            final Member author, final Study study, final ArticleRequest request
    ) {
        ResponseEntity<CreatedTempArticleIdResponse> response = sut
                .createTempArticle(author.getId(), study.getId(), request);
        entityManager.flush();
        entityManager.clear();
        return response;
    }
}
