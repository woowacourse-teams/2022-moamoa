package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.fixtures.MemberFixtures;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.AsyncService;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.SynchronizedParticipantService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class StudyParticipantControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AsyncService asyncService;

    private StudyParticipantService studyParticipantService;
    private SynchronizedParticipantService synchronizedParticipantService;

    private SlackUsersClient slackUsersClient;
    private SlackAlarmSender slackAlarmSender;

    private Member jjanggu;
    private Member dwoo;

    @BeforeEach
    void initDataBase() {
        studyParticipantService = new StudyParticipantService(memberRepository, studyRepository, new DateTimeSystem());
        synchronizedParticipantService = new SynchronizedParticipantService(studyParticipantService);

        slackUsersClient = mock(SlackUsersClient.class);
        when(slackUsersClient.getUserChannelByEmail("dwoo@moamoa.space")).thenReturn("dwoo-channel");

        slackAlarmSender = mock(SlackAlarmSender.class);
        doNothing().when(slackAlarmSender).requestSlackMessage("dwoo-channel");

        jjanggu = memberRepository.save(MemberFixtures.짱구());
        dwoo = memberRepository.save(MemberFixtures.디우());
    }

    @DisplayName("회원은 스터디에 참여할 수 있다.")
    @Test
    void participateStudy() {
        // given
        StudyController studyController = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(10)
                .tagIds(List.of(1L, 2L))
                .build();

        final ResponseEntity<Void> createdResponse = studyController.createStudy(jjanggu.getId(), studyRequest);

        // when
        final String location = createdResponse.getHeaders().getLocation().getPath();
        final long studyId = getStudyIdBy(location);

        final StudyParticipantController sut = new StudyParticipantController(synchronizedParticipantService, asyncService);
        final ResponseEntity<Void> response = sut.participateStudy(dwoo.getId(), studyId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    private long getStudyIdBy(final String location) {
        final String[] splitLocation = location.split("/");
        return Long.parseLong(splitLocation[3]);
    }

    @DisplayName("사용자가 스터디를 탈퇴한다.")
    @Test
    void leaveStudy() {
        // given
        StudyController studyController = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
                .title("java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(2)
                .tagIds(List.of(1L, 2L))
                .build();

        final ResponseEntity<Void> createdResponse = studyController.createStudy(jjanggu.getId(), studyRequest);

        // when
        final String location = createdResponse.getHeaders().getLocation().getPath();
        final long studyId = getStudyIdBy(location);
        Study study = studyRepository.findById(studyId).orElseThrow();

        final Member green = memberRepository.save(MemberFixtures.그린론());
        study.participate(green.getId());

        entityManager.flush();
        entityManager.clear();

        final StudyParticipantController sut = new StudyParticipantController(synchronizedParticipantService, asyncService);
        sut.leaveStudy(green.getId(), studyId);

        // then
        final Study leaveStudy = studyRepository.findById(studyId).orElseThrow();

        assertThat(leaveStudy.getContent().getTitle()).isEqualTo("java");
        assertThat(leaveStudy.getContent().getExcerpt()).isEqualTo("java excerpt");
        assertThat(leaveStudy.getContent().getThumbnail()).isEqualTo("java image");
        assertThat(leaveStudy.getContent().getDescription()).isEqualTo("자바 스터디 상세설명 입니다.");
        assertThat(leaveStudy.getRecruitPlanner().getMaxMemberCount()).isEqualTo(2);
        assertThat(leaveStudy.getRecruitPlanner().getRecruitStatus()).isNotEqualTo(
                study.getRecruitPlanner().getRecruitStatus());
    }
}
