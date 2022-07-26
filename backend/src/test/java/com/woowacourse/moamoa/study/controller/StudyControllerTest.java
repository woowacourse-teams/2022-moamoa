package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.CreatingStudyService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class StudyControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void initDataBase() {
        memberRepository.save(new Member(1L, "jjanggu", "https://image", "github.com"));
    }

    @DisplayName("스터디를 생성하여 저장한다.")
    @Test
    void openStudy() {
        // given
        StudyController sut = new StudyController(new CreatingStudyService(memberRepository, studyRepository));
        final CreatingStudyRequest creatingStudyRequest = CreatingStudyRequest.builder()
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
        final ResponseEntity<Void> response = sut.createStudy(1L, creatingStudyRequest);

        // then
        final String id = response.getHeaders().getLocation().getPath().replace("/api/studies/", "");
        Long studyId = Long.valueOf(id);
        Optional<Study> study = studyRepository.findById(studyId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(study).isNotEmpty();
        assertThat(study.get().getDetails()).isEqualTo(new Details("Java", "java excerpt", "java image", "OPEN", "자바 스터디 상세설명 입니다."));
        assertThat(study.get().getParticipants()).isEqualTo(Participants.createByMaxSizeAndOwnerId(10,
                memberRepository.findByGithubId(1L).get().getId()));
        assertThat(study.get().getCreatedAt()).isNotNull();
        assertThat(study.get().getPeriod()).isEqualTo(
                new Period(LocalDate.parse(creatingStudyRequest.getEnrollmentEndDate()),
                        creatingStudyRequest.getStartDate(), LocalDate.parse(creatingStudyRequest.getEndDate())));
        assertThat(study.get().getAttachedTags().getValue())
                .extracting("tagId").containsAnyElementsOf(creatingStudyRequest.getTagIds());
    }

    @DisplayName("유효하지 않은 스터디 기간으로 생성 시 예외 발생")
    @Test
    void createStudyByInvalidPeriod() {
        StudyController sut = new StudyController(new CreatingStudyService(memberRepository, studyRepository));
        final CreatingStudyRequest creatingStudyRequest = CreatingStudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(10)
                .tagIds(List.of(1L, 2L))
                .build();

        // when
        assertThatThrownBy(() -> sut.createStudy(1L, creatingStudyRequest))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("존재하지 않은 사용자로 생성 시 예외 발생")
    @Test
    void createStudyByNotFoundUser() {
        StudyController sut = new StudyController(new CreatingStudyService(memberRepository, studyRepository));
        final CreatingStudyRequest creatingStudyRequest = CreatingStudyRequest.builder()
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

        // when
        assertThatThrownBy(() -> sut.createStudy(100L, creatingStudyRequest)) // 존재하지 않는 사용자로 추가 시 예외 발생
                .isInstanceOf(UnauthorizedException.class);
    }
}
