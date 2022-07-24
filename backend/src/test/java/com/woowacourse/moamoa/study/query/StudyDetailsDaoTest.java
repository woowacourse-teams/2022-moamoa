package com.woowacourse.moamoa.study.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class StudyDetailsDaoTest {

    @Autowired
    private StudyDetailsDao sut;

    @DisplayName("모집 기간과 스터디 종료 일자가 없는 스터디 세부사항 조회")
    @Test
    void getNotHasEnrollmentEndDateAndEndDateStudyDetails() {
        // 알고리즘 스터디는 모집 기간과 스터디 종료일자가 없음
        final StudyDetailsData actual = sut.getById(5L);

        StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(5L).title("알고리즘 스터디").excerpt("알고리즘 설명").thumbnail("algorithm thumbnail")
                .status("CLOSE").description("알고리즘을 TDD로 풀자의 베루스입니다.").createdAt(actual.getCreatedAt())
                // Study Participants
                .maxMemberCount(2).currentMemberCount(1)
                .owner(new MemberData(4L, "verus", "https://image", "github.com"))
                // Study Period
                .startDate(LocalDate.of(2021, 12, 6))
                .build();

        assertStudyContent(actual, expect);
        assertStudyParticipants(actual, expect);
        assertStudyPeriod(actual, expect);
    }

    @DisplayName("최대 인원의 정보가 없는 스터디 세부사항 조회")
    @Test
    void getNotHasMaxMemberCountStudyDetails() {
        // Linux 스터디는 최대 인원 정보가 없음
        final StudyDetailsData actual = sut.getById(6L);

        StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(6L).title("Linux 스터디").excerpt("리눅스 설명").thumbnail("linux thumbnail")
                .status("CLOSE").description("Linux를 공부하자의 베루스입니다.").createdAt(actual.getCreatedAt())
                // Study Participant
                .currentMemberCount(1)
                .owner(new MemberData(4L, "verus", "https://image", "github.com"))
                // Study Period
                .startDate(LocalDate.of(2021, 12, 6))
                .enrollmentEndDate(LocalDate.of(2021, 12, 7))
                .endDate(LocalDate.of(2022, 1, 7))
                .build();

        assertStudyContent(actual, expect);
        assertStudyParticipants(actual, expect);
        assertStudyPeriod(actual, expect);
    }

    private void assertStudyContent(final StudyDetailsData actual, final StudyDetailsData expect) {
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getTitle()).isEqualTo(expect.getTitle());
        assertThat(actual.getExcerpt()).isEqualTo(expect.getExcerpt());
        assertThat(actual.getThumbnail()).isEqualTo(expect.getThumbnail());
        assertThat(actual.getStatus()).isEqualTo(expect.getStatus());
        assertThat(actual.getDescription()).isEqualTo(expect.getDescription());
        assertThat(actual.getCreatedAt()).isNotNull();
    }

    private void assertStudyParticipants(final StudyDetailsData actual, final StudyDetailsData expect) {
        assertThat(actual.getCurrentMemberCount()).isEqualTo(expect.getCurrentMemberCount());
        assertThat(actual.getMaxMemberCount()).isEqualTo(expect.getMaxMemberCount());
        assertThat(actual.getOwner()).isEqualTo(expect.getOwner());
    }

    private void assertStudyPeriod(final StudyDetailsData actual, final StudyDetailsData expect) {
        assertThat(actual.getEnrollmentEndDate()).isEqualTo(expect.getEnrollmentEndDate());
        assertThat(actual.getEndDate()).isEqualTo(expect.getEndDate());
        assertThat(actual.getStartDate()).isEqualTo(expect.getStartDate());
    }
}
