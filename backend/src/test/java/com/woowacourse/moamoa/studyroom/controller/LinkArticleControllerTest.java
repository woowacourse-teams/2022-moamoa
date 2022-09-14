package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.LinkArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.LinkNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    private EntityManager entityManager;

    private LinkArticleController sut;

    private Long jjangguId;
    private Long verusId;
    private Long dwooId;
    private Long javaStudyId;
    private Long linkId;

    @BeforeEach
    void setUp() {
        sut = new LinkArticleController(
                new LinkArticleService(studyRoomRepository, memberRepository, studyRepository, linkArticleRepository)
        );

        // 사용자 추가
        jjangguId = memberRepository.save(짱구()).getId();
        verusId = memberRepository.save(베루스()).getId();
        dwooId = memberRepository.save(디우()).getId();

        // 스터디 생성
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        final LocalDate startDate = LocalDate.now();
        final StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);

        javaStudyId = studyService.createStudy(짱구_깃허브_아이디, javaStudyRequest).getId();

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository);
        participantService.participateStudy(verusId, javaStudyId);

        // 링크 공유 생성
        final LinkArticleService linkArticleService =
                new LinkArticleService(studyRoomRepository, memberRepository, studyRepository, linkArticleRepository);
        final LinkArticleRequest linkArticleRequest =
                new LinkArticleRequest("https://github.com/sc0116", "링크 설명입니다.");

        linkId = linkArticleService.createLink(jjangguId, javaStudyId, linkArticleRequest).getId();

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("스터디에 참여하지 않은 회원은 링크 공유를 할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final LinkArticleRequest linkArticleRequest =
                new LinkArticleRequest("https://github.com/sc0116", "링크 설명입니다.");

        assertThatThrownBy(() -> sut.createLink(dwooId, javaStudyId, linkArticleRequest))
                .isInstanceOf(UneditableArticleException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final LinkArticleRequest editingLinkRequest = new LinkArticleRequest("www.naver.com", "수정");

        assertThatThrownBy(() -> sut.updateLink(jjangguId, javaStudyId, -1L, editingLinkRequest))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final LinkArticleRequest editingLinkRequest = new LinkArticleRequest("https://github.com", "수정된 링크 설명입니다.");

        assertThatThrownBy(() -> sut.updateLink(dwooId, javaStudyId, linkId, editingLinkRequest))
                .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByInvalidLinkId() {
        assertThatThrownBy(() -> sut.deleteLink(jjangguId, javaStudyId, -1L))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotParticipatedMember() {
        assertThatThrownBy(() -> sut.deleteLink(dwooId, javaStudyId, linkId))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}
