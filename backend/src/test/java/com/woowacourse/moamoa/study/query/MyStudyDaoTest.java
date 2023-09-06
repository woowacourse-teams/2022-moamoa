package com.woowacourse.moamoa.study.query;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.HTTP_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디_계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디_모집계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리눅스_스터디_참가자들;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_모집계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.리액트_스터디_참가자들;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디_계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디_모집계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.알고리즘_스터디_참가자들;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_모집계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_참가자들;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디_계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디_내용;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디_모집계획;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바스크립트_스터디_참가자들;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.alarm.service.alarmsender.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.service.alarmuserclient.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class MyStudyDaoTest {

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

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

    @DisplayName("내가 참여한 스터디 목록을 조회한다.")
    @Test
    void getMyStudies() {
        final List<MyStudySummaryData> studySummaryData = myStudyDao.findMyStudyByMemberId(그린론.getId());

        assertThat(studySummaryData)
                .hasSize(5)
                .filteredOn(myStudySummaryData -> myStudySummaryData.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount", "startDate", "endDate")
                .containsExactlyInAnyOrder(
                        tuple(자바_스터디_내용.getTitle(), 자바_스터디_계획.getStudyStatus(), 자바_스터디_참가자들.getSize(),
                                자바_스터디_모집계획.getMaxMemberCount(), 자바_스터디_계획.getStartDate().toString(),
                                자바_스터디_계획.getEndDate().toString()),
                        tuple(리액트_스터디_내용.getTitle(), 리액트_스터디_계획.getStudyStatus(), 리액트_스터디_참가자들.getSize(),
                                리액트_스터디_모집계획.getMaxMemberCount(), 리액트_스터디_계획.getStartDate().toString(),
                                자바_스터디_계획.getEndDate().toString()),
                        tuple(자바스크립트_스터디_내용.getTitle(), 자바스크립트_스터디_계획.getStudyStatus(), 자바스크립트_스터디_참가자들.getSize(),
                                자바스크립트_스터디_모집계획.getMaxMemberCount(), 자바스크립트_스터디_계획.getStartDate().toString(),
                                자바스크립트_스터디_계획.getEndDate().toString()),
                        tuple(알고리즘_스터디_내용.getTitle(), 알고리즘_스터디_계획.getStudyStatus(), 알고리즘_스터디_참가자들.getSize(),
                                알고리즘_스터디_모집계획.getMaxMemberCount(), 알고리즘_스터디_계획.getStartDate().toString(), null),
                        tuple(리눅스_스터디_내용.getTitle(), 리눅스_스터디_계획.getStudyStatus(), 리눅스_스터디_참가자들.getSize(),
                                리눅스_스터디_모집계획.getMaxMemberCount(), 리눅스_스터디_계획.getStartDate().toString(),
                                리눅스_스터디_계획.getEndDate().toString())
                );
    }

    @DisplayName("스터디 ID가 비어있을 경우, 스터디 방장 빈 맵을 반환한다.")
    @Test
    void findStudyOwnersByEmptyStudyId() {
        final Map<Long, MemberData> owners = myStudyDao.findOwners(List.of());

        assertThat(owners).isEmpty();
    }

    @DisplayName("스터디 ID가 비어있을 경우, 스터디 태그 빈 맵을 반환한다.")
    @Test
    void findStudyTagsByEmptyStudyId() {
        final Map<Long, List<TagSummaryData>> tags = myStudyDao.findTags(List.of());

        assertThat(tags).isEmpty();
    }
}
