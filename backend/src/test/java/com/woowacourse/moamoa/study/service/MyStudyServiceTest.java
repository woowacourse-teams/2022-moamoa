package com.woowacourse.moamoa.study.service;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.HTTP_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.OS_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MyStudyServiceTest {

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    private MyStudyService myStudyService;

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
    private Study OS_스터디;

    @BeforeEach
    void setUp() {
        myStudyService = new MyStudyService(myStudyDao, memberRepository, studyRepository);

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
        OS_스터디 = studyRepository.save(OS_스터디(디우.getId(), Set.of(그린론.getId(), 짱구.getId(), 베루스.getId())));

        entityManager.flush();
    }

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void findMyStudies() {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(짱구.getId());

        final List<MemberData> owners = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        final List<List<TagSummaryData>> tags = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getTags)
                .collect(toList());

        final List<MyStudyResponse> studies = myStudiesResponse.getStudies();

        assertThat(studies)
                .hasSize(4)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .contains(
                        tuple(자바_스터디.getContent().getTitle(), 자바_스터디.getStudyPlanner().getStudyStatus(), 자바_스터디.getParticipants().getSize(), 자바_스터디.getRecruitPlanner().getMax()),
                        tuple(리액트_스터디.getContent().getTitle(), 리액트_스터디.getStudyPlanner().getStudyStatus(), 리액트_스터디.getParticipants().getSize(), 리액트_스터디.getRecruitPlanner().getMax()),
                        tuple(HTTP_스터디.getContent().getTitle(), HTTP_스터디.getStudyPlanner().getStudyStatus(), HTTP_스터디.getParticipants().getSize(), HTTP_스터디.getRecruitPlanner().getMax()),
                        tuple(OS_스터디.getContent().getTitle(), OS_스터디.getStudyPlanner().getStudyStatus(), OS_스터디.getParticipants().getSize(), OS_스터디.getRecruitPlanner().getMax())
                );

        assertThat(owners)
                .hasSize(4)
                .extracting("id", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(짱구.getId(), 짱구.getUsername(), 짱구.getImageUrl(), 짱구.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl())
                );

        assertThat(tags).hasSize(4);
    }

    @DisplayName("태그가 없는 스터디를 조회한다.")
    @Test
    void findMyStudiesWithoutTags() {
        final MyStudiesResponse myStudiesResponse = myStudyService.getStudies(디우.getId());

        final List<MemberData> owners = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        final List<List<TagSummaryData>> tags = myStudiesResponse.getStudies()
                .stream()
                .map(MyStudyResponse::getTags)
                .collect(toList());

        final List<MyStudyResponse> studies = myStudiesResponse.getStudies();

        assertThat(studies)
                .hasSize(7)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .contains(
                        tuple(자바_스터디.getContent().getTitle(), 자바_스터디.getStudyPlanner().getStudyStatus(),
                                자바_스터디.getParticipants().getSize(), 자바_스터디.getRecruitPlanner().getMax()),
                        tuple(리액트_스터디.getContent().getTitle(), 리액트_스터디.getStudyPlanner().getStudyStatus(),
                                리액트_스터디.getParticipants().getSize(), 리액트_스터디.getRecruitPlanner().getMax()),
                        tuple(자바스크립트_스터디.getContent().getTitle(), 자바스크립트_스터디.getStudyPlanner().getStudyStatus(),
                                자바스크립트_스터디.getParticipants().getSize(), 자바스크립트_스터디.getRecruitPlanner().getMax()),
                        tuple(HTTP_스터디.getContent().getTitle(), HTTP_스터디.getStudyPlanner().getStudyStatus(),
                                HTTP_스터디.getParticipants().getSize(), HTTP_스터디.getRecruitPlanner().getMax()),
                        tuple(알고리즘_스터디.getContent().getTitle(), 알고리즘_스터디.getStudyPlanner().getStudyStatus(),
                                알고리즘_스터디.getParticipants().getSize(), 알고리즘_스터디.getRecruitPlanner().getMax()),
                        tuple(리눅스_스터디.getContent().getTitle(), 리눅스_스터디.getStudyPlanner().getStudyStatus(),
                                리눅스_스터디.getParticipants().getSize(), 리눅스_스터디.getRecruitPlanner().getMax()),
                        tuple(OS_스터디.getContent().getTitle(), OS_스터디.getStudyPlanner().getStudyStatus(),
                                OS_스터디.getParticipants().getSize(), OS_스터디.getRecruitPlanner().getMax())
                );

        assertThat(owners)
                .hasSize(7)
                .extracting("id", "username", "imageUrl", "profileUrl")
                .contains(
                        tuple(짱구.getId(), 짱구.getUsername(), 짱구.getImageUrl(), 짱구.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        tuple(그린론.getId(), 그린론.getUsername(), 그린론.getImageUrl(), 그린론.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        tuple(베루스.getId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl()),
                        tuple(베루스.getId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl())
                );

        assertThat(tags.get(4)).isEmpty();
        assertThat(tags.get(5)).isEmpty();
    }

    @DisplayName("존재하지 않은 내가 참여한 스터디 조회 시 예외 발생")
    @Test
    void getMyStudyNotExistUser() {
        assertThatThrownBy(() -> myStudyService.getStudies(5L))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다.");
    }

    @DisplayName("사용자 역할 조회하는 기능에서 존재하지 않는 사용자 조회 시 예외 발생")
    @Test
    void getMemberRoleNotExistUser() {
        assertThatThrownBy(() -> myStudyService.findMyRoleInStudy(5L, 1L))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다.");
    }

    @DisplayName("사용자 역할 조회하는 기능에서 존재하지 않는 스터디 조회 시 예외 발생")
    @Test
    void getMemberRoleNotExistStudy() {
        final Long memberId = 짱구.getId();
        assertThatThrownBy(() -> myStudyService.findMyRoleInStudy(memberId, 10L))
                .isInstanceOf(StudyNotFoundException.class)
                .hasMessageContaining("스터디가 존재하지 않습니다.");
    }
}
