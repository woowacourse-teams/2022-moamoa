package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.InvalidUpdatingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RecruitPlannerTest {

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

    @DisplayName("Recruit Planner| 스터디 상태 수정| 모집 마감 날짜 수정일이 생성일 이전으로 수정 OR 수정 인원이 현재 인원 보다 적은 경우 예외")
    @ParameterizedTest
    @CsvSource({"1,3", "0,2"})
    void updateRecruitPlannerException(int modifyCreateDate, int memberCount) {
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
        final RecruitPlanner updateRecruitPlanner = new RecruitPlanner(memberCount, RECRUITMENT_END, createdAt.toLocalDate()
                .minusDays(modifyCreateDate));

        assertThatThrownBy(() -> {
            study.updatePlanners(updateRecruitPlanner, studyPlanner, now);
            study.updateContent(1L, content, AttachedTags.empty());
        }).isInstanceOf(InvalidUpdatingException.class)
                .hasMessageContaining("스터디 수정이 불가능합니다.");
    }

    @DisplayName("Recruit Planner| 스터디 상태 수정| 수정일이 모집 마감 날짜 이전 AND 수정 인원이 현재 인원과 같은 경우 END")
    @Test
    void updateRecruitPlannerIsEnd() {
        //given
        LocalDateTime now = now();
        LocalDateTime createdAt = now;

        LocalDate enrollmentEndDate = now.toLocalDate().plusDays(2);
        LocalDate startDate = now.toLocalDate().plusDays(2);
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

    @DisplayName("Recruit Planner| 스터디 상태 수정| 수정일이 모집 마감 날짜 이전 AND 수정 인원이 현재 인원과 같은 경우 START")
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
}
