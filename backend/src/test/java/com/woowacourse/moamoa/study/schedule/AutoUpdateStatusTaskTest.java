package com.woowacourse.moamoa.study.schedule;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.woowacourse.moamoa.alarm.service.alarmsender.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.service.alarmuserclient.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.fixtures.MemberFixtures;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class AutoUpdateStatusTaskTest {

    private long javaStudyId;
    private long reactStudyId;
    private long javascriptStudyId;
    private long httpStudyId;
    private long linuxStudyId;
    private long algorithmStudyId;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    DateTimeSystem dateTimeSystem;

    private TriggerTask sut;

    private LocalDateTime now;

    @BeforeEach
    void initDataBase() {
        now = LocalDateTime.now();
        dateTimeSystem = mock(DateTimeSystem.class);
        given(dateTimeSystem.now()).willReturn(now.minusDays(5));

        final Member jjanggu = memberRepository.save(MemberFixtures.짱구());
        final Member dwoo = memberRepository.save(MemberFixtures.디우());

        StudyService studyService = new StudyService(studyRepository, memberRepository, dateTimeSystem);

        StudyRequest javaRequest = StudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("그린론의 우당탕탕 자바 스터디입니다.")
                .startDate(dateTimeSystem.now().toLocalDate().plusDays(3))
                .enrollmentEndDate(dateTimeSystem.now().toLocalDate().plusDays(4))
                .build();
        javaStudyId = studyService.createStudy(jjanggu.getId(), javaRequest).getId();

        StudyRequest reactRequest = StudyRequest.builder()
                .title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail")
                .description("디우의 뤼액트 스터디입니다.")
                .startDate(dateTimeSystem.now().toLocalDate().plusDays(2))
                .enrollmentEndDate(dateTimeSystem.now().toLocalDate().plusDays(3))
                .build();
        reactStudyId = studyService.createStudy(jjanggu.getId(), reactRequest).getId();

        StudyRequest javascriptRequest = StudyRequest.builder()
                .title("javaScript 스터디").excerpt("자바스크립트 설명").thumbnail("javascript thumbnail")
                .description("그린론의 자바스크립트 접해보기")
                .startDate(dateTimeSystem.now().toLocalDate().plusDays(8))
                .enrollmentEndDate(dateTimeSystem.now().toLocalDate().plusDays(7))
                .build();
        javascriptStudyId = studyService.createStudy(jjanggu.getId(), javascriptRequest).getId();

        StudyRequest httpRequest = StudyRequest.builder()
                .title("Http 스터디").excerpt("Http 설명").thumbnail("http thumbnail")
                .description("그린론의 HTTP 접해보기")
                .startDate(dateTimeSystem.now().toLocalDate().plusDays(8))
                .build();
        httpStudyId = studyService.createStudy(jjanggu.getId(), httpRequest).getId();

        StudyRequest linuxRequest = StudyRequest.builder()
                .title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .description("Linux를 공부하자의 베루스입니다.")
                .startDate(dateTimeSystem.now().toLocalDate())
                .endDate(dateTimeSystem.now().toLocalDate())
                .build();
        linuxStudyId = studyService.createStudy(jjanggu.getId(), linuxRequest).getId();

        StudyRequest algorithmRequest = StudyRequest.builder()
                .title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail")
                .description("알고리즘을 TDD로 풀자의 베루스입니다.")
                .startDate(dateTimeSystem.now().toLocalDate().plusDays(2))
                .endDate(dateTimeSystem.now().toLocalDate().plusDays(5))
                .build();

        algorithmStudyId = studyService.createStudy(jjanggu.getId(), algorithmRequest).getId();

        sut = new AutoCloseEnrollmentTask(studyService);
    }

    @DisplayName("자동 모집 종료 태스크는 매일 00시 00분에 실행된다.")
    @ParameterizedTest
    @MethodSource("provideCurrentTimeAndExecuteTime")
    void taskWillExecuteDaily(LocalDateTime currentTime, LocalDateTime expectExecuteTime) {
        final Trigger trigger = sut.getTrigger();

        assertNextExecuteTime(trigger, currentTime, expectExecuteTime);
    }

    private static Stream<Arguments> provideCurrentTimeAndExecuteTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2021, 12, 31, 23, 59, 59),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
                Arguments.of(LocalDateTime.of(2022, 3, 2, 0, 0, 1),
                        LocalDateTime.of(2022, 3, 3, 0, 0, 0))
        );
    }

    private void assertNextExecuteTime(Trigger trigger, LocalDateTime current, LocalDateTime expectNextExecuteTime) {
        final Date date = Date.from(current.toInstant(ZoneOffset.of("+9")));
        final LocalDateTime actualNextExecuteTime = trigger
                .nextExecutionTime(new SimpleTriggerContext(date, date, date))
                .toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        assertThat(actualNextExecuteTime).isEqualTo(expectNextExecuteTime);
    }

    @DisplayName("스터디의 상태를 자동으로 변경시킨다.")
    @Test
    void autoUpdateStatus() {
        given(dateTimeSystem.now()).willReturn(LocalDateTime.now());

        sut.getRunnable().run();

        final Study javaStudy = studyRepository.findById(javaStudyId).orElseThrow();
        final Study reactStudy = studyRepository.findById(reactStudyId).orElseThrow();
        final Study javascriptStudy = studyRepository.findById(javascriptStudyId).orElseThrow();
        final Study httpStudy = studyRepository.findById(httpStudyId).orElseThrow();
        final Study linuxStudy = studyRepository.findById(linuxStudyId).orElseThrow();

        assertThat(javaStudy.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
        assertThat(reactStudy.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
        assertThat(javascriptStudy.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
        assertThat(httpStudy.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
        assertThat(linuxStudy.isCloseStudy()).isTrue();
    }
}
