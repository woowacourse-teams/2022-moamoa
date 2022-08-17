package com.woowacourse.moamoa.referenceroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.referenceroom.domain.repository.LinkRepository;
import com.woowacourse.moamoa.referenceroom.service.ReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.exception.LinkNotFoundException;
import com.woowacourse.moamoa.referenceroom.service.exception.NotCreatingLinkException;
import com.woowacourse.moamoa.referenceroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.referenceroom.service.request.LinkRequest;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class ReferenceRoomControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private EntityManager entityManager;

    private ReferenceRoomController sut;

    private Long jjangguId;
    private Long verusId;
    private Long dwooId;
    private Long javaStudyId;
    private Long linkId;

    @BeforeEach
    void setUp() {
        sut = new ReferenceRoomController(new ReferenceRoomService(memberRepository, studyRepository, linkRepository));

        // 사용자 추가
        jjangguId = memberRepository.save(짱구()).getId();
        verusId = memberRepository.save(베루스()).getId();
        dwooId = memberRepository.save(디우()).getId();

        // 스터디 생성
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        final LocalDate startDate = LocalDate.now();
        final StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);

        javaStudyId = studyService.createStudy(짱구_깃허브_아이디, javaStudyRequest).getId();
        studyService.participateStudy(베루스_깃허브_아이디, javaStudyId);

        // 링크 공유 생성
        final ReferenceRoomService referenceRoomService =
                new ReferenceRoomService(memberRepository, studyRepository, linkRepository);
        final LinkRequest linkRequest =
                new LinkRequest("https://github.com/sc0116", "링크 설명입니다.");

        linkId = referenceRoomService.createLink(짱구_깃허브_아이디, javaStudyId, linkRequest).getId();

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("스터디에 참여하지 않은 회원은 링크 공유를 할 수 없다.")
    @Test
    void createByNotParticipatedMember() {
        final LinkRequest linkRequest =
                new LinkRequest("https://github.com/sc0116", "링크 설명입니다.");

        assertThatThrownBy(() -> sut.createLink(디우_깃허브_아이디, javaStudyId, linkRequest))
                .isInstanceOf(NotCreatingLinkException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByInvalidLinkId() {
        final LinkRequest linkRequest = new LinkRequest("www.naver.com", "수정");

        assertThatThrownBy(() -> sut.updateLink(짱구_깃허브_아이디, javaStudyId, -1L, linkRequest))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 수정할 수 없다.")
    @Test
    void updateByNotParticipatedMember() {
        final LinkRequest linkRequest = new LinkRequest("https://github.com", "수정된 링크 설명입니다.");

        assertThatThrownBy(() -> sut.updateLink(디우_깃허브_아이디, javaStudyId, linkId, linkRequest))
                .isInstanceOf(NotParticipatedMemberException.class);
    }

    @DisplayName("존재하지 않는 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByInvalidLinkId() {
        assertThatThrownBy(() -> sut.deleteLink(짱구_깃허브_아이디, javaStudyId, -1L))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @DisplayName("스터디에 참여하지 않은 경우 링크 공유글을 삭제할 수 없다.")
    @Test
    void deleteByNotParticipatedMember() {
        assertThatThrownBy(() -> sut.deleteLink(디우_깃허브_아이디, javaStudyId, linkId))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}
