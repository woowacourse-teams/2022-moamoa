package com.woowacourse.moamoa.study.controller;

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
import static com.woowacourse.moamoa.fixtures.TagFixtures.BE_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.BE_태그명;
import static com.woowacourse.moamoa.fixtures.TagFixtures.FE_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.FE_태그명;
import static com.woowacourse.moamoa.fixtures.TagFixtures.리액트_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.리액트_태그명;
import static com.woowacourse.moamoa.fixtures.TagFixtures.우테코4기_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.우테코4기_태그명;
import static com.woowacourse.moamoa.fixtures.TagFixtures.자바_태그_아이디;
import static com.woowacourse.moamoa.fixtures.TagFixtures.자바_태그명;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.MyStudyDao;
import com.woowacourse.moamoa.study.service.MyStudyService;
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
    void setUp() {
        myStudyController = new MyStudyController(new MyStudyService(myStudyDao, memberRepository, studyRepository));

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

    @DisplayName("내가 참여한 스터디를 조회한다.")
    @Test
    void getMyStudies() {
        final ResponseEntity<MyStudiesResponse> myStudies = myStudyController.getMyStudies(그린론.getId());

        assertThat(myStudies.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(myStudies.getBody()).isNotNull();
        assertThat(myStudies.getBody().getStudies())
                .hasSize(5)
                .filteredOn(myStudy -> myStudy.getId() != null)
                .extracting("title", "studyStatus", "currentMemberCount", "maxMemberCount")
                .containsExactlyElementsOf(List.of(
                        tuple(자바_스터디_내용.getTitle(), 자바_스터디_계획.getStudyStatus(), 자바_스터디_참가자들.getSize(),
                                자바_스터디_모집계획.getMax()),
                        tuple(리액트_스터디_내용.getTitle(), 리액트_스터디_계획.getStudyStatus(), 리액트_스터디_참가자들.getSize(),
                                리액트_스터디_모집계획.getMax()),
                        tuple(자바스크립트_스터디_내용.getTitle(), 자바스크립트_스터디_계획.getStudyStatus(), 자바스크립트_스터디_참가자들.getSize(),
                                자바스크립트_스터디_모집계획.getMax()),
                        tuple(알고리즘_스터디_내용.getTitle(), 알고리즘_스터디_계획.getStudyStatus(), 알고리즘_스터디_참가자들.getSize(),
                                알고리즘_스터디_모집계획.getMax()),
                        tuple(리눅스_스터디_내용.getTitle(), 리눅스_스터디_계획.getStudyStatus(), 리눅스_스터디_참가자들.getSize(),
                                리눅스_스터디_모집계획.getMax())
                ));

        final List<MemberData> owners = myStudies.getBody()
                .getStudies()
                .stream()
                .map(MyStudyResponse::getOwner)
                .collect(toList());

        assertThat(owners)
                .hasSize(5)
                .extracting("id", "username", "imageUrl", "profileUrl")
                .containsExactlyInAnyOrder(
                        tuple(짱구.getId(), 짱구.getUsername(), 짱구.getImageUrl(), 짱구.getProfileUrl()),
                        tuple(디우.getId(), 디우.getUsername(), 디우.getImageUrl(), 디우.getProfileUrl()),
                        tuple(그린론.getId(), 그린론.getUsername(), 그린론.getImageUrl(), 그린론.getProfileUrl()),
                        tuple(베루스.getId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl()),
                        tuple(베루스.getId(), 베루스.getUsername(), 베루스.getImageUrl(), 베루스.getProfileUrl())
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
                        tuple(자바_태그_아이디, 자바_태그명),
                        tuple(우테코4기_태그_아이디, 우테코4기_태그명),
                        tuple(BE_태그_아이디, BE_태그명))
                );

        assertThat(tags.get(1))
                .hasSize(3)
                .extracting("id", "name")
                .contains(
                        tuple(리액트_태그_아이디, 리액트_태그명),
                        tuple(우테코4기_태그_아이디, 우테코4기_태그명),
                        tuple(FE_태그_아이디, FE_태그명)
                );

        assertThat(tags.get(2))
                .hasSize(2)
                .extracting("id", "name")
                .contains(
                        tuple(우테코4기_태그_아이디, 우테코4기_태그명),
                        tuple(FE_태그_아이디, FE_태그명)
                );


        assertThat(tags.get(3)).isEmpty();
        assertThat(tags.get(4)).isEmpty();
    }
}
