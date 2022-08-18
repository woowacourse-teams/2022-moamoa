package com.woowacourse.moamoa.study.query;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.HTTP_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.OwnerData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class StudyDetailsDaoTest {

    @Autowired
    private StudyDetailsDao sut;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    private Member 짱구;
    private Member 그린론;
    private Member 디우;
    private Member 베루스;

    private Study 자바_스터디;
    private Study 리액트_스터디;
    private Study 자바스크립트_스터디;
    private Study HTTP_스터디;
    private Study 알고리즘_스터디;
    private Study 리눅스_스터디;

    @BeforeEach
    void initDataBase() {
        짱구 = memberRepository.save(짱구());
        그린론 = memberRepository.save(그린론());
        디우 = memberRepository.save(디우());
        베루스 = memberRepository.save(베루스());

        자바_스터디 = studyRepository.save(자바_스터디(짱구.getId(), Set.of(그린론.getId(), 디우.getId())));
        리액트_스터디 = studyRepository.save(리액트_스터디(디우.getId(), Set.of(짱구.getId(), 그린론.getId(), 베루스.getId())));
        자바스크립트_스터디 = studyRepository.save(자바스크립트_스터디(그린론.getId(), Set.of(디우.getId(), 베루스.getId())));
        HTTP_스터디 = studyRepository.save(HTTP_스터디(디우.getId(), Set.of(베루스.getId(), 짱구.getId())));
        알고리즘_스터디 = studyRepository.save(알고리즘_스터디(베루스.getId(), Set.of(그린론.getId(), 디우.getId())));
        리눅스_스터디 = studyRepository.save(리눅스_스터디(베루스.getId(), Set.of(그린론.getId(), 디우.getId())));

        entityManager.flush();
    }


    @DisplayName("모집 기간과 스터디 종료 일자가 없는 스터디 세부사항 조회")
    @Test
    void getNotHasEnrollmentEndDateAndEndDateStudyDetails() {
        // 알고리즘 스터디는 모집 기간과 스터디 종료일자가 없음
        final StudyDetailsData actual = sut.findBy(알고리즘_스터디.getId()).orElseThrow();

        StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(알고리즘_스터디.getId()).title(알고리즘_스터디.getContent().getTitle())
                .excerpt(알고리즘_스터디.getContent().getExcerpt()).thumbnail(알고리즘_스터디.getContent().getThumbnail())
                .status(알고리즘_스터디.getRecruitPlanner().getRecruitStatus().toString())
                .description(알고리즘_스터디.getContent().getDescription()).createdDate(actual.getCreatedDate())
                // Study Participants
                .currentMemberCount(알고리즘_스터디.getParticipants().getSize())
                .owner(new OwnerData(베루스.getGithubId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl(), LocalDate.now(), 5))
                // Study Period
                .startDate(알고리즘_스터디.getStudyPlanner().getStartDate())
                .build();

        assertStudyContent(actual, expect);
        assertStudyParticipants(actual, expect);
        assertStudyPeriod(actual, expect);
    }

    @DisplayName("최대 인원의 정보가 없는 스터디 세부사항 조회")
    @Test
    void getNotHasMaxMemberCountStudyDetails() {
        // Linux 스터디는 최대 인원 정보가 없음
        final StudyDetailsData actual = sut.findBy(리눅스_스터디.getId()).orElseThrow();

        StudyDetailsData expect = StudyDetailsData.builder()
                // Study Content
                .id(리눅스_스터디.getId()).title(리눅스_스터디.getContent().getTitle()).excerpt(리눅스_스터디.getContent().getExcerpt()).thumbnail(리눅스_스터디.getContent().getThumbnail())
                .status(리눅스_스터디.getRecruitPlanner().getRecruitStatus().toString()).description(리눅스_스터디.getContent().getDescription()).createdDate(actual.getCreatedDate())
                // Study Participant
                .currentMemberCount(리눅스_스터디.getParticipants().getSize())
                .owner(new OwnerData(베루스.getGithubId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl(), LocalDate.now(), 5))
                // Study Period
                .startDate(리눅스_스터디.getStudyPlanner().getStartDate())
                .enrollmentEndDate(리눅스_스터디.getRecruitPlanner().getEnrollmentEndDate())
                .endDate(리눅스_스터디.getStudyPlanner().getEndDate())
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
        assertThat(actual.getRecruitmentStatus()).isEqualTo(expect.getRecruitmentStatus());
        assertThat(actual.getDescription()).isEqualTo(expect.getDescription());
        assertThat(actual.getCreatedDate()).isNotNull();
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
