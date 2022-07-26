package com.woowacourse.moamoa.study.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class StudyServiceTest {

    private StudyService studyService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update(
                "INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (1, 1, 'jjanggu', 'https://image', 'github.com')");
        jdbcTemplate.update(
                "INSERT INTO member(id, github_id, username, image_url, profile_url) VALUES (2, 2, 'greenlawn', 'https://image', 'github.com')");
    }

    @BeforeEach
    void setUp() {
        this.studyService = new StudyService(studyRepository, memberRepository);
    }

    @DisplayName("회원은 스터디를 생성할 수 있다.")
    @Test
    public void createStudy() {
        final Member member = memberRepository.findByGithubId(1L).get();

        final CreatingStudyRequest creatingStudyRequest = new CreatingStudyRequest("title", "excerpt", "thumbnail",
                "description", 10, LocalDate.now().plusDays(1), LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(1), List.of());

        assertDoesNotThrow(() -> studyService.createStudy(member.getGithubId(), creatingStudyRequest));
        final Study study = studyRepository.findById(1L).get();
        assertThat(study.getParticipants().getParticipants().size()).isEqualTo(1);
    }

    @DisplayName("아직 참여하지 않았고 모집기간이 마감되지 않은 열려있는 스터디에 대해서 회원은 스터디에 참여할 수 있다.")
    @Test
    public void participantStudy() {
        // given
        final Member member = memberRepository.findByGithubId(1L).get();

        final CreatingStudyRequest creatingStudyRequest = new CreatingStudyRequest("title", "excerpt", "thumbnail",
                "description", 10, LocalDate.now().plusDays(1), LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(1), List.of());

        final Study createdStudy = studyService.createStudy(member.getGithubId(), creatingStudyRequest);

        // when
        final Study foundStudy = studyRepository.findById(createdStudy.getId()).get();
        final Member participant = memberRepository.findByGithubId(2L).get();

        studyService.participantStudy(participant.getGithubId(), foundStudy.getId());
        entityManager.flush();

        // then
        final Study result = studyRepository.findById(foundStudy.getId()).get();
        assertThat(result.getParticipants().getParticipants().size()).isEqualTo(2);
        assertThat(result.getParticipants().getParticipants().contains(new Participant(2L))).isTrue();
    }
}


