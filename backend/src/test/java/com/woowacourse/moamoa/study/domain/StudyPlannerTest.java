package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;

import static java.time.LocalDateTime.now;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudyPlannerTest {

    @DisplayName("시작일자는 종료일자보다 클 수 없다.")
    @Test
    void startDateMustBeforeEndDate() {
        assertThatThrownBy(() -> new StudyPlanner(
                LocalDate.of(2022, 7, 10),
                LocalDate.of(2022, 7, 9), StudyStatus.PREPARE))
                .isInstanceOf(InvalidPeriodException.class);
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

    @DisplayName("Study Planner| 스터디 수정| 스터디 시작이 종료 이후 OR 스터디 생성이 시작 이후 예외")
    @ParameterizedTest
    @CsvSource({"1, 0", "-1, 0"})
    void updateStudyPlannerException(int startDay, int endDay) {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate();

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when && then
        assertThatThrownBy(() -> {
            StudyPlanner updateStudyPlanner = new StudyPlanner(startDate.plusDays(startDay), endDate.plusDays(endDay), IN_PROGRESS);
            study.updatePlanners(recruitPlanner, updateStudyPlanner, now);
            study.updateContent(1L, content, AttachedTags.empty());
        });
    }

    @DisplayName("Study Planner| 스터디 수정| 수정하는 날이 종료일 이후인 경우 DONE")
    @Test
    void updateStudyPlannerIsDone() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate();

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(4, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when
        final StudyPlanner updateStudyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);
        study.updatePlanners(recruitPlanner, updateStudyPlanner, now.plusDays(1));
        study.updateContent(1L, content, AttachedTags.empty());

        //then
        assertThat(study.getStudyPlanner().getStudyStatus()).isEqualTo(DONE);
    }

    @DisplayName("Study Planner| 스터디 수정| 수정하는 날이 시작일 이전인 경우 PREPARE")
    @Test
    void updateStudyPlannerIsPrepare() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate().plusDays(3);
        LocalDate endDate = now.toLocalDate().plusDays(4);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(4, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, PREPARE);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when
        final StudyPlanner updateStudyPlanner = new StudyPlanner(startDate, endDate, PREPARE);
        study.updatePlanners(recruitPlanner, updateStudyPlanner, now.plusDays(2));
        study.updateContent(1L, content, AttachedTags.empty());

        //then
        assertThat(study.getStudyPlanner().getStudyStatus()).isEqualTo(PREPARE);
    }

    @DisplayName("Study Planner| 스터디 수정| 수정하는 날이 종료일 이후거나 수정하는 날이 시작일 이전인 경우가 아니면 IN_PROGRESS")
    @Test
    void updateStudyPlannerIsInProgress() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate();

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(4, RECRUITMENT_START, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when
        final StudyPlanner updateStudyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);
        study.updatePlanners(recruitPlanner, updateStudyPlanner, now);
        study.updateContent(1L, content, AttachedTags.empty());

        //then
        assertThat(study.getStudyPlanner().getStudyStatus()).isEqualTo(IN_PROGRESS);
    }
}
