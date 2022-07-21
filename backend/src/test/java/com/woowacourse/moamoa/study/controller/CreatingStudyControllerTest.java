package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.controller.request.OpenStudyRequest;
import com.woowacourse.moamoa.study.domain.study.Details;
import com.woowacourse.moamoa.study.domain.study.Participants;
import com.woowacourse.moamoa.study.domain.study.Period;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.study.domain.study.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.CreateStudyService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class CreatingStudyControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("스터디를 생성하여 저장한다.")
    @Test
    void openStudy() {
        // given
        CreatingStudyController sut = new CreatingStudyController(new CreateStudyService(memberRepository, studyRepository));
        final OpenStudyRequest openStudyRequest = OpenStudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().plusDays(3))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(10)
                .tagIds(List.of(1L, 2L))
                .build();

        // when
        final ResponseEntity<Void> response = sut.createStudy(1L, openStudyRequest);

        // then
        final String id = response.getHeaders().getLocation().getPath().replace("/api/studies/", "");
        Long studyId = Long.valueOf(id);
        Optional<Study> study = studyRepository.findById(studyId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(study).isNotEmpty();
        assertThat(study.get().getDetails()).isEqualTo(new Details("Java", "java excerpt", "java image", "OPEN", "자바 스터디 상세설명 입니다."));
        assertThat(study.get().getParticipants()).isEqualTo(Participants.createByMaxSize(10));
        assertThat(study.get().getOwner().getGithubId()).isEqualTo(1L);
        assertThat(study.get().getCreatedAt()).isNotNull();
        assertThat(study.get().getPeriod()).isEqualTo(
                new Period(openStudyRequest.getEnrollmentEndDateTime(), openStudyRequest.getStartDateTime(), openStudyRequest.getEndDateTime()));
        assertThat(study.get().getAttachedTags())
                .extracting("tagId").containsAnyElementsOf(openStudyRequest.getTagIds());
    }

    @DisplayName("")
    @Test
    void createStudyByInvalidPeriod() {
        // given

        // when

        // then
    }
}
