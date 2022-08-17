package com.woowacourse.moamoa.study.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class StudyParticipantControllerTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member jjanggu;
    private Member dwoo;

    @BeforeEach
    void initDataBase() {
        jjanggu = memberRepository.save(new Member(1L, "jjanggu", "https://image", "github.com"));
        dwoo = memberRepository.save(new Member(2L, "dwoo", "https://image", "github.com"));
    }

    @DisplayName("회원은 스터디에 참여할 수 있다.")
    @Test
    void participateStudy() {
        // given
        StudyController studyController = new StudyController(new StudyService(studyRepository, memberRepository,
                new DateTimeSystem()));
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

        final ResponseEntity<Void> createdResponse = studyController.createStudy(jjanggu.getGithubId(), creatingStudyRequest);

        // when
        final String location = createdResponse.getHeaders().getLocation().getPath();
        final long studyId = getStudyIdBy(location);

        final StudyParticipantController sut = new StudyParticipantController(
                new StudyParticipantService(memberRepository, studyRepository));
        final ResponseEntity<Void> response = sut.participateStudy(dwoo.getId(), studyId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    private long getStudyIdBy(final String location) {
        final String[] splitLocation = location.split("/");
        return Long.parseLong(splitLocation[3]);
    }
}
