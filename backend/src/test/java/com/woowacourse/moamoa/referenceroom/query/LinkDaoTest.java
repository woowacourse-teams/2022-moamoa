package com.woowacourse.moamoa.referenceroom.query;

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
public class LinkDaoTest {

    private static final MemberData JJANGGU = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData GREENLAWN = new MemberData(2L, "greenlawn", "https://image", "github.com");
    private static final MemberData DWOO = new MemberData(3L, "dwoo", "https://image", "github.com");
    private static final MemberData VERUS = new MemberData(4L, "verus", "https://image", "github.com");

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
        final Member jjanggu = memberRepository.save(toMember(JJANGGU));
        final Member greenlawn = memberRepository.save(toMember(GREENLAWN));
        final Member dwoo = memberRepository.save(toMember(DWOO));
        final Member verus = memberRepository.save(toMember(VERUS));

        // 스터디 생성
        StudyService createStudyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);

        javaStudy = createStudyService.createStudy(JJANGGU.getGithubId(), javaStudyRequest);

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository);
        participantService.participateStudy(greenlawn.getId(), javaStudy.getId());
        participantService.participateStudy(dwoo.getId(), javaStudy.getId());
        participantService.participateStudy(verus.getId(), javaStudy.getId());

        // 링크 공유 추가
        final ReferenceRoomService linkService = new ReferenceRoomService(memberRepository, studyRepository, linkRepository);

        final CreatingLinkRequest request1 = new CreatingLinkRequest("https://github.com/sc0116", "짱구 링크.");
        final CreatingLinkRequest request2 = new CreatingLinkRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final CreatingLinkRequest request3 = new CreatingLinkRequest("https://github.com/tco0427", "디우 링크.");
        final CreatingLinkRequest request4 = new CreatingLinkRequest("https://github.com/wilgur513", "베루스 링크.");

        final Link link1 = linkService.createLink(JJANGGU.getGithubId(), javaStudy.getId(), request1);
        final Link link2 = linkService.createLink(GREENLAWN.getGithubId(), javaStudy.getId(), request2);
        final Link link3 = linkService.createLink(DWOO.getGithubId(), javaStudy.getId(), request3);
        final Link link4 = linkService.createLink(VERUS.getGithubId(), javaStudy.getId(), request4);

        entityManager.flush();
        entityManager.clear();

        final LinkData 링크1 = new LinkData(link1.getId(), JJANGGU, link1.getLinkUrl(), link1.getDescription(), link1.getCreatedDate().toLocalDate(), link1.getLastModifiedDate().toLocalDate());
        final LinkData 링크2 = new LinkData(link2.getId(), GREENLAWN, link2.getLinkUrl(), link2.getDescription(), link2.getCreatedDate().toLocalDate(), link2.getLastModifiedDate().toLocalDate());
        final LinkData 링크3 = new LinkData(link3.getId(), DWOO, link3.getLinkUrl(), link3.getDescription(), link3.getCreatedDate().toLocalDate(), link3.getLastModifiedDate().toLocalDate());
        final LinkData 링크4 = new LinkData(link4.getId(), VERUS, link4.getLinkUrl(), link4.getDescription(), link4.getCreatedDate().toLocalDate(), link4.getLastModifiedDate().toLocalDate());

        linkData = List.of(링크1, 링크2, 링크3, 링크4);
    }

    private Member toMember(final MemberData data) {
        return new Member(data.getGithubId(), data.getUsername(), data.getImageUrl(), data.getProfileUrl());
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
}
