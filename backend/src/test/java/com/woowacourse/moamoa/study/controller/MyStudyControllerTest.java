package com.woowacourse.moamoa.study.controller;

import static com.woowacourse.fixtures.MemberFixtures.그린론;
import static com.woowacourse.fixtures.MemberFixtures.그린론_깃허브_아이디;
import static com.woowacourse.fixtures.MemberFixtures.디우;
import static com.woowacourse.fixtures.MemberFixtures.베루스;
import static com.woowacourse.fixtures.MemberFixtures.짱구;
import static com.woowacourse.fixtures.StudyFixtures.HTTP_스터디;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디_계획;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디_내용;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디_모집계획;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디_참가자들;
import static com.woowacourse.fixtures.StudyFixtures.리눅스_스터디장;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디_계획;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디_내용;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디_모집계획;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디_참가자들;
import static com.woowacourse.fixtures.StudyFixtures.리액트_스터디장;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디_계획;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디_내용;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디_모집계획;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디_참가자들;
import static com.woowacourse.fixtures.StudyFixtures.알고리즘_스터디장;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_계획;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_내용;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_모집계획;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디_참가자들;
import static com.woowacourse.fixtures.StudyFixtures.자바_스터디장;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디_계획;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디_내용;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디_모집계획;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디_참가자들;
import static com.woowacourse.fixtures.StudyFixtures.자바스크립트_스터디장;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.MyStudyService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.response.MyStudiesResponse;
import com.woowacourse.moamoa.study.service.response.MyStudyResponse;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class MyStudyControllerTest {

    @Autowired
    private MyStudyDao myStudyDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    private MyStudyController myStudyController;

    private StudyService studyService;

    @BeforeEach
    void setUp() {
        myStudyController = new MyStudyController(new MyStudyService(myStudyDao, memberRepository, studyRepository));

        memberRepository.save(짱구);
        memberRepository.save(그린론);
        memberRepository.save(디우);
        memberRepository.save(베루스);

        studyRepository.save(자바_스터디);
        studyRepository.save(리액트_스터디);
        studyRepository.save(자바스크립트_스터디);
        studyRepository.save(HTTP_스터디);
        studyRepository.save(알고리즘_스터디);
        studyRepository.save(리눅스_스터디);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() {
        final ResponseEntity<MyStudiesResponse> myStudies = myStudyController.getMyStudies(그린론_깃허브_아이디);

        assertThat(myStudies.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(myStudies.getBody()).isNotNull();
        assertThat(myStudies.getBody().getStudies())
                .hasSize(5)
                .extracting("id", "title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, 자바_스터디_내용.getTitle(), 자바_스터디_계획.getStudyStatus(), 자바_스터디_참가자들.getSize(),
                                자바_스터디_모집계획.getMax()),
                        tuple(2L, 리액트_스터디_내용.getTitle(), 리액트_스터디_계획.getStudyStatus(), 리액트_스터디_참가자들.getSize(),
                                리액트_스터디_모집계획.getMax()),
                        tuple(3L, 자바스크립트_스터디_내용.getTitle(), 자바스크립트_스터디_계획.getStudyStatus(), 자바스크립트_스터디_참가자들.getSize(),
                                자바스크립트_스터디_모집계획.getMax()),
                        tuple(5L, 알고리즘_스터디_내용.getTitle(), 알고리즘_스터디_계획.getStudyStatus(), 알고리즘_스터디_참가자들.getSize(),
                                알고리즘_스터디_모집계획.getMax()),
                        tuple(6L, 리눅스_스터디_내용.getTitle(), 리눅스_스터디_계획.getStudyStatus(), 리눅스_스터디_참가자들.getSize(),
                                리눅스_스터디_모집계획.getMax())
                ));

        final List<MemberData> owners = myStudies.getBody()
                .getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        assertThat(owners)
                .hasSize(5)
                .extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactlyInAnyOrder(
                        tuple(자바_스터디장.getGithubId(), 자바_스터디장.getUsername(), 자바_스터디장.getImageUrl(), 자바_스터디장.getProfileUrl()),
                        tuple(리액트_스터디장.getGithubId(), 리액트_스터디장.getUsername(), 리액트_스터디장.getImageUrl(), 리액트_스터디장.getProfileUrl()),
                        tuple(자바스크립트_스터디장.getGithubId(), 자바스크립트_스터디장.getUsername(), 자바스크립트_스터디장.getImageUrl(), 자바스크립트_스터디장.getProfileUrl()),
                        tuple(알고리즘_스터디장.getGithubId(), 알고리즘_스터디장.getUsername(), 알고리즘_스터디장.getImageUrl(), 알고리즘_스터디장.getProfileUrl()),
                        tuple(리눅스_스터디장.getGithubId(), 리눅스_스터디장.getUsername(), 리눅스_스터디장.getImageUrl(), 리눅스_스터디장.getProfileUrl())
                );

        final List<List<TagSummaryData>> tags = myStudies.getBody()
                .getStudies()
                .stream()
                .map(MyStudyResponse::getTags)
                .collect(toList());

        assertThat(tags.get(0))
                .hasSize(3)
                .extracting("id", "name")
                .containsExactlyElementsOf(List.of(
                        tuple(1L, "Java"),
                        tuple(2L, "4기"),
                        tuple(3L, "BE"))
                );

        assertThat(tags.get(1))
                .hasSize(3)
                .extracting("id", "name")
                .contains(tuple(5L, "React"),
                        tuple(2L, "4기"),
                        tuple(4L, "FE"));

        assertThat(tags.get(2))
                .hasSize(2)
                .extracting("id", "name")
                .contains(tuple(2L, "4기"),
                        tuple(4L, "FE"));


        assertThat(tags.get(3).size()).isZero();
        assertThat(tags.get(4).size()).isZero();
    }
}
