package com.woowacourse.moamoa.referenceroom.query;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.referenceroom.domain.repository.LinkRepository;
import com.woowacourse.moamoa.referenceroom.query.data.LinkData;
import com.woowacourse.moamoa.referenceroom.service.ReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.StudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@RepositoryTest
class LinkDaoTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LinkDao sut;

    private Study javaStudy;

    private List<LinkData> linkData;

    @BeforeEach
    void setUp() {
        // 사용자 추가
        final Member 짱구 = memberRepository.save(짱구());
        final Member 그린론 = memberRepository.save(그린론());
        final Member 디우 = memberRepository.save(디우());
        final Member 베루스 = memberRepository.save(베루스());

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);

        javaStudy = createStudyService.createStudy(짱구.getId(), javaStudyRequest);

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository);
        participantService.participateStudy(그린론 .getId(), javaStudy.getId());
        participantService.participateStudy(디우.getId(), javaStudy.getId());
        participantService.participateStudy(베루스.getId(), javaStudy.getId());

        // 링크 공유 추가
        final ReferenceRoomService linkService = new ReferenceRoomService(memberRepository, studyRepository, linkRepository);

        final CreatingLinkRequest request1 = new CreatingLinkRequest("https://github.com/sc0116", "짱구 링크.");
        final CreatingLinkRequest request2 = new CreatingLinkRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final CreatingLinkRequest request3 = new CreatingLinkRequest("https://github.com/tco0427", "디우 링크.");
        final CreatingLinkRequest request4 = new CreatingLinkRequest("https://github.com/wilgur513", "베루스 링크.");

        final Link link1 = linkService.createLink(짱구.getId(), javaStudy.getId(), request1);
        final Link link2 = linkService.createLink(그린론 .getId(), javaStudy.getId(), request2);
        final Link link3 = linkService.createLink(디우.getId(), javaStudy.getId(), request3);
        final Link link4 = linkService.createLink(베루스.getId(), javaStudy.getId(), request4);

        entityManager.flush();
        entityManager.clear();

        final MemberData 짱구_응답 = toMemberData(짱구);
        final MemberData 그린론_응답 = toMemberData(그린론);
        final MemberData 디우_응답 = toMemberData(디우);
        final MemberData 베루스_응답 = toMemberData(베루스);

        final LinkData 링크1 = new LinkData(link1.getId(), 짱구_응답, link1.getLinkUrl(), link1.getDescription(), link1.getCreatedDate().toLocalDate(), link1.getLastModifiedDate().toLocalDate());
        final LinkData 링크2 = new LinkData(link2.getId(), 그린론_응답, link2.getLinkUrl(), link2.getDescription(), link2.getCreatedDate().toLocalDate(), link2.getLastModifiedDate().toLocalDate());
        final LinkData 링크3 = new LinkData(link3.getId(), 디우_응답, link3.getLinkUrl(), link3.getDescription(), link3.getCreatedDate().toLocalDate(), link3.getLastModifiedDate().toLocalDate());
        final LinkData 링크4 = new LinkData(link4.getId(), 베루스_응답, link4.getLinkUrl(), link4.getDescription(), link4.getCreatedDate().toLocalDate(), link4.getLastModifiedDate().toLocalDate());

        linkData = List.of(링크1, 링크2, 링크3, 링크4);
    }

    @DisplayName("스터디 ID로 링크 공유글을 조회한다.")
    @Test
    void getLinks() {
        final Slice<LinkData> links = sut.findAllByStudyId(javaStudy.getId(), PageRequest.of(0, 5));

        assertAll(
                () -> assertThat(links.hasNext()).isFalse(),
                () -> assertThat(links.getContent())
                        .containsExactlyInAnyOrderElementsOf(linkData)
        );
    }

    private MemberData toMemberData(final Member member) {
        return new MemberData(member.getId(), member.getUsername(), member.getImageUrl(), member.getProfileUrl());
    }
}
