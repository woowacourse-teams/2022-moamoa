package com.woowacourse.moamoa.study.service;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class StudyParticipantServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    private StudyParticipantService sut;

    @BeforeEach
    void setUp() {
        sut = new StudyParticipantService(memberRepository, studyRepository, new DateTimeSystem());
    }

    @DisplayName("스터디가 존재하지 않는 경우 스터디원 강퇴 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenKickOutToNotFoundStudy() {
        final Long 짱구_아이디 = saveMember(짱구());
        final Long 디우_아이디 = saveMember(디우());

        assertThatThrownBy(() -> sut.kickOutMember(짱구_아이디, 1L, 디우_아이디))
                .isInstanceOf(StudyNotFoundException.class);
   }

    private Long saveMember(final Member member) {
        final Member savedMember = memberRepository.save(member);
        entityManager.flush();
        entityManager.clear();
        return savedMember.getId();
    }

    private Long createStudy(final Long ownerId, StudyRequest studyRequest) {
        final StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
        final Study study = studyService.createStudy(ownerId, studyRequest);
        entityManager.flush();
        entityManager.clear();
        return study.getId();
    }
}
