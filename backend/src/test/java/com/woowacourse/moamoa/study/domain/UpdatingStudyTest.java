package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.DONE;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.service.exception.InvalidUpdatingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdatingStudyTest {

    @DisplayName("Recruit Planner| 수정하려는 모집 마감 일이 생성일 이전인 경우 예외가 발생한다.")
    @Test
    void updateRecruitPlannerExceptionIfStudyCreateAfterStudyEnrollmentEnd() {
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
        study.participate(2L);
        study.participate(3L);

        //when && then
        final RecruitPlanner updateRecruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, createdAt.toLocalDate()
                .minusDays(1));

        assertThatThrownBy(() -> {
            study.updatePlanners(updateRecruitPlanner, studyPlanner, now);
            study.updateContent(1L, content, AttachedTags.empty());
        }).isInstanceOf(InvalidUpdatingException.class)
                .hasMessageContaining("스터디 수정이 불가능합니다.");
    }

    @DisplayName("Recruit Planner| 수정 인원이 현재 인원 보다 적은 경우 예외가 발생한다.")
    @Test
    void updateRecruitPlannerExceptionIfMemberNumberLessThanPresentMember() {
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
        study.participate(2L);
        study.participate(3L);

        //when && then
        final RecruitPlanner updateRecruitPlanner = new RecruitPlanner(2, RECRUITMENT_END, createdAt.toLocalDate());

        assertThatThrownBy(() -> {
            study.updatePlanners(updateRecruitPlanner, studyPlanner, now);
            study.updateContent(1L, content, AttachedTags.empty());
        }).isInstanceOf(InvalidUpdatingException.class)
                .hasMessageContaining("스터디 수정이 불가능합니다.");
    }

    @DisplayName("Recruit Planner| 수정하는 현재 날짜가 모집 마감 날짜 이전이고, 수정 인원이 현재 인원과 같은 경우 모집 상태는 END이다.")
    @Test
    void updateRecruitPlannerIsEnd() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(3);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when && then
        final RecruitPlanner updateRecruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, enrollmentEndDate);
        study.updatePlanners(updateRecruitPlanner, studyPlanner, now.plusDays(3));
        study.updateContent(1L, content, AttachedTags.empty());

        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("Recruit Planner| 수정하는 현재 날짜가 모집 마감 날짜 이전이고, 현재 인원이 수정 인원보다 적은 경우 스터디 모집(START) 상태이다.")
    @Test
    void updateRecruitPlannerIsStart() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate();
        LocalDate startDate = now.toLocalDate();
        LocalDate endDate = now.toLocalDate().plusDays(3);

        Content content = new Content("title", "excerpt", "thumbnail", "description");
        Participants participants = Participants.createBy(1L);
        RecruitPlanner recruitPlanner = new RecruitPlanner(3, RECRUITMENT_END, enrollmentEndDate);
        StudyPlanner studyPlanner = new StudyPlanner(startDate, endDate, IN_PROGRESS);

        Study study = new Study(content, participants, recruitPlanner, studyPlanner, AttachedTags.empty(), createdAt);
        study.participate(2L);
        study.participate(3L);

        //when && then
        final RecruitPlanner updateRecruitPlanner = new RecruitPlanner(4, RECRUITMENT_END, enrollmentEndDate);
        study.updatePlanners(updateRecruitPlanner, studyPlanner, now);
        study.updateContent(1L, content, AttachedTags.empty());

        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_START);
    }

    @DisplayName("Study Planner| 스터디 시작일이 종료일 이후인 경우 예외가 발생한다.")
    @Test
    void updateStudyPlannerExceptionIfStudyStartAfterStudyEnd() {
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
            StudyPlanner updateStudyPlanner = new StudyPlanner(startDate.plusDays(1), endDate, IN_PROGRESS);
            study.updatePlanners(recruitPlanner, updateStudyPlanner, now);
            study.updateContent(1L, content, AttachedTags.empty());
        });
    }

    @DisplayName("Study Planner| 수정하는 날이 스터디 종료일 이후인 경우 스터디 상태는 DONE이다.")
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

    @DisplayName("Study Planner| 수정하는 날이 스터디 시작일 이전인 경우 PREPARE 상태이다.")
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

    @DisplayName("Study Planner| 수정하는 날이 스터디 종료일 이후이거나, 수정하는 날이 시작일 이전인 경우가 아니면 IN_PROGRESS 상태이다.")
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
