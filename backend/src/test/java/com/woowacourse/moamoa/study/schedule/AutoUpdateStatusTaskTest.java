package com.woowacourse.moamoa.study.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
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
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.SimpleTriggerContext;

@RepositoryTest
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

        memberRepository.save(new Member(1L, "jjanggu", "https://image", "github.com"));
        memberRepository.save(new Member(2L, "dwoo", "https://image", "github.com"));

        StudyService studyService = new StudyService(studyRepository, memberRepository, dateTimeSystem);

        CreatingStudyRequest javaRequest = CreatingStudyRequest.builder()
                .title("Java 스터디").excerpt("자바 설명").thumbnail("java thumbnail")
                .description("그린론의 우당탕탕 자바 스터디입니다.")
                .startDate(now.toLocalDate().minusDays(2))
                .enrollmentEndDate(now.toLocalDate().minusDays(1))
                .build();
        javaStudyId = studyService.createStudy(1L, javaRequest).getId();

        CreatingStudyRequest reactRequest = CreatingStudyRequest.builder()
                .title("React 스터디").excerpt("리액트 설명").thumbnail("react thumbnail")
                .description("디우의 뤼액트 스터디입니다.")
                .startDate(now.toLocalDate().minusDays(3))
                .enrollmentEndDate(now.toLocalDate().minusDays(2))
                .build();
        reactStudyId = studyService.createStudy(1L, reactRequest).getId();

        CreatingStudyRequest javascriptRequest = CreatingStudyRequest.builder()
                .title("javaScript 스터디").excerpt("자바스크립트 설명").thumbnail("javascript thumbnail")
                .description("그린론의 자바스크립트 접해보기")
                .startDate(now.toLocalDate().plusDays(3))
                .enrollmentEndDate(now.toLocalDate().plusDays(2))
                .build();
        javascriptStudyId = studyService.createStudy(1L, javascriptRequest).getId();

        CreatingStudyRequest httpRequest = CreatingStudyRequest.builder()
                .title("Http 스터디").excerpt("Http 설명").thumbnail("http thumbnail")
                .description("그린론의 HTTP 접해보기")
                .startDate(now.toLocalDate().plusDays(3))
                .build();
        httpStudyId = studyService.createStudy(1L, httpRequest).getId();

        CreatingStudyRequest linuxRequest = CreatingStudyRequest.builder()
                .title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .description("Linux를 공부하자의 베루스입니다.")
                .startDate(now.toLocalDate().minusDays(5))
                .endDate(now.toLocalDate())
                .build();
        linuxStudyId = studyService.createStudy(1L, linuxRequest).getId();

        CreatingStudyRequest algorithmRequest = CreatingStudyRequest.builder()
                .title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail")
                .description("알고리즘을 TDD로 풀자의 베루스입니다.")
                .startDate(now.toLocalDate().minusDays(2))
                .endDate(now.toLocalDate())
                .build();

        algorithmStudyId = studyService.createStudy(1L, algorithmRequest).getId();

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
        final LocalDateTime actualNextExecuteTime =
                trigger.nextExecutionTime(new SimpleTriggerContext(date, date, date))
                .toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        assertThat(actualNextExecuteTime).isEqualTo(expectNextExecuteTime);
    }

    @DisplayName("모집 기간이 지난 스터디는 자동으로 모집이 종료된다.")
    @Test
    void autoCloseEnrollment() {
        given(dateTimeSystem.now()).willReturn(LocalDateTime.now());

        sut.getRunnable().run();

        final Study javaStudy = studyRepository.findById(javaStudyId).orElseThrow();
        final Study reactStudy = studyRepository.findById(reactStudyId).orElseThrow();
        final Study javascriptStudy = studyRepository.findById(javascriptStudyId).orElseThrow();
        final Study httpStudy = studyRepository.findById(httpStudyId).orElseThrow();

        assertThat(javaStudy.isCloseEnrollment()).isEqualTo(true);
        assertThat(reactStudy.isCloseEnrollment()).isEqualTo(true);
        assertThat(javascriptStudy.isCloseEnrollment()).isEqualTo(false);
        assertThat(httpStudy.isCloseEnrollment()).isEqualTo(false);
    }

    @DisplayName("스터디 종료기간이 넘으면 자동으로 종료 상태가 된다.")
    @Test
    public void autoCloseStudyStatus() {
        given(dateTimeSystem.now()).willReturn(LocalDateTime.now());

        sut.getRunnable().run();

        final Study linuxStudy = studyRepository.findById(linuxStudyId).orElseThrow();

        assertThat(linuxStudy.isCloseStudy()).isEqualTo(true);
    }

    @DisplayName("스터디 시작기간(StartDate)이 되면 자동으로 진행중 상태가 된다.")
    @Test
    public void autoProgressStudyStatus() {
        given(dateTimeSystem.now()).willReturn(LocalDateTime.now().minusDays(2));

        sut.getRunnable().run();

        final Study linuxStudy = studyRepository.findById(algorithmStudyId).orElseThrow();

        assertThat(linuxStudy.isProgressStatus()).isEqualTo(true);
    }
}
