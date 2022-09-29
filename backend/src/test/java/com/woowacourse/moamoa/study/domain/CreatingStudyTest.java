package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CreatingStudyTest {

    @DisplayName("RECRUIT Planner| 스터디 상태 생성 | 모집 마감일 < 생성일 예외")
    @Test
    void createStudyRecruitStatusException() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate().minusDays(1);
        LocalDate startDate = now.toLocalDate();
        //target
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(1, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when && then
        assertThatThrownBy(
                () -> new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt))
                .isInstanceOf(InvalidPeriodException.class)
                .hasMessageContaining("잘못된 기간 설정입니다.");
    }

    @DisplayName("RECRUIT Planner| 스터디 상태 생성 | 모집 인원 1명 End")
    @Test
    void createStudyRecruitStatusIsEnd() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);

        //target
        RecruitPlanner recruitPlanner = new RecruitPlanner(1, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);

        //then
        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("RECRUIT Planner| 스터디 상태 생성 | 예외와 END 이외의 경우 START")
    @Test
    void createStudyRecruitStatusIsStart() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);

        //then
        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
    }

    @DisplayName("STUDY Planner| 스터디 생성 | 시작이 종료보다 이후인 경우 OR 생성이 시작보다 이후인 경우 예외")
    @ParameterizedTest
    @CsvSource({"0,1", "1,0"})
    void createStudyPlannerStatusException(int modifyStartDate, int modifyEndDate) {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        //target
        LocalDate startDate = now.toLocalDate().minusDays(modifyStartDate);
        LocalDate endDate = now.toLocalDate().minusDays(modifyEndDate);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(1, RECRUITMENT_START, enrollmentEndDate);

        //when && then
        assertThatThrownBy(
                () -> {
                    StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);
                    new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
                })
                .isInstanceOf(InvalidPeriodException.class)
                .hasMessageContaining("잘못된 기간 설정입니다.");
    }

    @DisplayName("STUDY Planner| 스터디 생성 |시작 == 생성 IN PROGRESS")
    @Test
    void createStudyPlannerStatusIsProgress() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);

        //target
        RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);

        //then
        assertThat(study.getStudyPlanner().getStudyStatus()).isEqualTo(IN_PROGRESS);
    }

    @DisplayName("STUDY Planner| 스터디 생성 | 예외와 IN PROGRESS 이외의 경우 PREPARE")
    @Test
    void createStudyPlannerStatusIsPrepare() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);

        //target
        RecruitPlanner recruitPlanner = new RecruitPlanner(2, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);

        //then
        assertThat(study.getStudyPlanner().getStudyStatus()).isEqualTo(IN_PROGRESS);
    }
}
