package com.woowacourse.moamoa.study.controller;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.CREATED;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.fixtures.MemberFixtures;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class StudyControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member 짱구;
    private Member 디우;

    @BeforeEach
    void initDataBase() {
        짱구 = memberRepository.save(MemberFixtures.짱구());
        디우 = memberRepository.save(MemberFixtures.디우());
    }

    @DisplayName("스터디를 생성하여 저장한다.")
    @Test
    void openStudy() {
        // given
        StudyController sut = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
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
        final ResponseEntity<Void> response = sut.createStudy(짱구.getId(), studyRequest);

        // then
        final String id = response.getHeaders().getLocation().getPath().replace("/api/studies/", "");
        Long studyId = Long.valueOf(id);
        Optional<Study> study = studyRepository.findById(studyId);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(study).isNotEmpty();
        assertThat(study.get().getContent()).isEqualTo(new Content("Java", "java excerpt",
                "java image", "자바 스터디 상세설명 입니다."));
        assertThat(study.get().getParticipants()).isEqualTo(Participants.createBy(
                memberRepository.findByGithubId(1L).get().getId()));
        assertThat(study.get().getCreatedAt()).isNotNull();
        assertThat(study.get().getStudyPlanner()).isEqualTo(
                new StudyPlanner(
                        studyRequest.getStartDate(), studyRequest.getEndDate(), PREPARE));
        assertThat(study.get().getAttachedTags().getValue())
                .extracting("tagId").containsAnyElementsOf(studyRequest.getTagIds());
    }

    @DisplayName("유효하지 않은 스터디 기간으로 생성 시 예외 발생")
    @Test
    void createStudyByInvalidPeriod() {
        StudyController sut = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
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
        final Long memberId = 짱구.getId();
        assertThatThrownBy(() -> sut.createStudy(memberId, studyRequest))
                .isInstanceOf(InvalidPeriodException.class);
    }

    @DisplayName("스터디 생성 날짜와 스터디 시작 날짜가 같은 경우 IN_PROGRESS로 설정한다.")
    @Test
    void checkStudyStatusIfCreateDateSameStartDate() {
        StudyController sut = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));

        final StudyRequest createStudyRequest = StudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(3))
                .maxMemberCount(10)
                .tagIds(List.of(1L, 2L))
                .build();

        final ResponseEntity<Void> response = sut.createStudy(짱구.getId(), createStudyRequest);
        final String id = response.getHeaders()
                .getLocation()
                .getPath()
                .replace("/api/studies/", "");

        Long studyId = Long.valueOf(id);
        Optional<Study> study = studyRepository.findById(studyId);

        assertThat(study.get().isProgressStatus()).isTrue();
    }

    @DisplayName("존재하지 않은 사용자로 생성 시 예외 발생")
    @Test
    void createStudyByNotFoundUser() {
        StudyController sut = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
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
        final Long memberId = 짱구.getId();
        assertThatThrownBy(() -> sut.createStudy(memberId + 100L, studyRequest)) // 존재하지 않는 사용자로 추가 시 예외 발생
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("최대인원이 한 명인 경우 바로 모집 종료가 되어야 한다.")
    @Test
    void createdStudyWithMaxSizeOne() {
        // given
        StudyController studyController = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
        final StudyRequest studyRequest = StudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(1)
                .tagIds(List.of(1L, 2L))
                .build();

        final ResponseEntity<Void> createdResponse = studyController.createStudy(짱구.getId(), studyRequest);

        // when
        final String location = createdResponse.getHeaders().getLocation().getPath();
        final long studyId = getStudyIdBy(location);
        final Study study = studyRepository.findById(studyId).get();

        // then
        assertThat(study.getRecruitPlanner().getRecruitStatus()).isEqualTo(RECRUITMENT_END);
    }

    @DisplayName("스터디 상세 정보를 업데이트할 수 있다.")
    @Test
    void updateStudyDetails() {
        // given
        StudyController studyController = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));

        final StudyRequest studyRequest = StudyRequest.builder()
                .title("Java")
                .excerpt("java excerpt")
                .thumbnail("java image")
                .description("자바 스터디 상세설명 입니다.")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(1)
                .tagIds(List.of(1L, 2L))
                .build();

        final ResponseEntity<Void> createdResponse = studyController.createStudy(짱구.getId(), studyRequest);
        final String location = createdResponse.getHeaders().getLocation().getPath();
        final long studyId = getStudyIdBy(location);
        Study study = studyRepository.findById(studyId).get();

        final StudyRequest updatingStudyRequest = StudyRequest.builder()
                .title("변경된 title")
                .excerpt("변경된 excerpt")
                .thumbnail("변경된 image")
                .description("변경된 상세설명")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(4))
                .enrollmentEndDate(LocalDate.now().plusDays(2))
                .maxMemberCount(10)
                .tagIds(List.of(1L))
                .build();

        // when
        studyController.updateStudy(study.getParticipants().getOwnerId(), studyId, updatingStudyRequest);

        // then
        study = studyRepository.findById(studyId).orElseThrow();
        assertThat(study.getContent().getTitle()).isEqualTo("변경된 title");
        assertThat(study.getContent().getExcerpt()).isEqualTo("변경된 excerpt");
        assertThat(study.getContent().getThumbnail()).isEqualTo("변경된 image");
        assertThat(study.getContent().getDescription()).isEqualTo("변경된 상세설명");
        assertThat(study.getRecruitPlanner().getMaxMemberCount()).isEqualTo(10);
        assertThat(study.getAttachedTags().getAttachedTags().get(0)).isEqualTo(new AttachedTag(1L));
    }

    private long getStudyIdBy(final String location) {
        final String[] splitLocation = location.split("/");
        return Long.parseLong(splitLocation[3]);
    }
}
