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

public class CreatingStudyTest {

    @DisplayName("RECRUIT Planner| 스터디 모집 마감일은 생성일보다 이전이면 예외가 발생한다.")
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

    @DisplayName("RECRUIT Planner| 스터디의 현재 인원(1명)이 모집 인원과 같은 경우 모집이 종료(END)된다.")
    @Test
    void createStudyRecruitStatusIsEnd() {
        //given
        LocalDateTime now = now();

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);

        //target
        RecruitPlanner recruitPlanner = new RecruitPlanner(1, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), now);

        //then
        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("RECRUIT Planner| 예외가 발생하지 않고, 모집 상태가 END가 아닌 경우 모집중(START)인 상태이다.")
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

    @DisplayName("RECRUIT Planner| 모집 인원이 null인 경우 모집중(START) 상태이다.")
    @Test
    void recruitMemberSizeIsNull() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(1);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(null, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        //when
        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);

        //then
        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
    }


    @DisplayName("STUDY Planner| 스터디 생성이 스터디 시작일 이후인 경우 예외가 발생한다.")
    @Test
    void createStudyPlannerStatusExceptionIfStudyCreateAfterStudyStart() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        //target
        LocalDate startDate = now.toLocalDate().minusDays(1);
        LocalDate endDate = now.toLocalDate().minusDays(0);

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

    @DisplayName("STUDY Planner| 스터디 시작이 스터디 종료일 이후인 경우 예외가 발생한다.")
    @Test
    void createStudyPlannerStatusExceptionIfStudyStartAfterStudyEnd() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        //target
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().minusDays(1);

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

    @DisplayName("STUDY Planner| 스터디 시작일과 생성일이 같은 경우, 스터디는 진행중(IN PROGRESS) 상태이다.")
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

    @DisplayName("STUDY Planner| 스터디 시작 일자 <= 스터디 종료 일자이인 경우(정상적인 경우)")
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
