package com.woowacourse.moamoa.referenceroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.병민;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.병민_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_응답;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.referenceroom.domain.repository.LinkRepository;
import com.woowacourse.moamoa.referenceroom.query.LinkDao;
import com.woowacourse.moamoa.referenceroom.query.data.LinkData;
import com.woowacourse.moamoa.referenceroom.service.ReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.SearchingReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.referenceroom.service.response.LinkResponse;
import com.woowacourse.moamoa.referenceroom.service.response.LinksResponse;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
public class SearchingReferenceRoomControllerTest {

    @Autowired
    private LinkDao linkDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private EntityManager entityManager;

    private SearchingReferenceRoomController sut;

    private Study javaStudy;

    private List<LinkResponse> linkResponses;

    @BeforeEach
    void setUp() {
        sut = new SearchingReferenceRoomController(
                new SearchingReferenceRoomService(linkDao, memberRepository, studyRepository));

        // 사용자 추가
        final Member jjanggu = memberRepository.save(짱구);
        final Member greenlawn = memberRepository.save(그린론);
        final Member dwoo = memberRepository.save(디우);
        final Member verus = memberRepository.save(베루스);
        memberRepository.save(병민);

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        javaStudy = studyService.createStudy(짱구_깃허브_아이디, javaStudyRequest);

        studyService.participateStudy(그린론_깃허브_아이디, javaStudy.getId());
        studyService.participateStudy(디우_깃허브_아이디, javaStudy.getId());
        studyService.participateStudy(베루스_깃허브_아이디, javaStudy.getId());

        // 링크 공유 추가
        final ReferenceRoomService linkService = new ReferenceRoomService(memberRepository, studyRepository,
                linkRepository);

        final CreatingLinkRequest request1 = new CreatingLinkRequest("https://github.com/sc0116", "짱구 링크.");
        final CreatingLinkRequest request2 = new CreatingLinkRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final CreatingLinkRequest request3 = new CreatingLinkRequest("https://github.com/tco0427", "디우 링크.");
        final CreatingLinkRequest request4 = new CreatingLinkRequest("https://github.com/wilgur513", "베루스 링크.");

        final Link link1 = linkService.createLink(짱구_깃허브_아이디, javaStudy.getId(), request1);
        final Link link2 = linkService.createLink(그린론_깃허브_아이디, javaStudy.getId(), request2);
        final Link link3 = linkService.createLink(디우_깃허브_아이디, javaStudy.getId(), request3);
        final Link link4 = linkService.createLink(베루스_깃허브_아이디, javaStudy.getId(), request4);

        entityManager.flush();
        entityManager.clear();

        final LinkResponse 링크1 = new LinkResponse(
                new LinkData(link1.getId(), 짱구_응답, link1.getLinkUrl(), link1.getDescription(),
                        link1.getCreatedDate().toLocalDate(), link1.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크2 = new LinkResponse(
                new LinkData(link2.getId(), 그린론_응답, link2.getLinkUrl(), link2.getDescription(),
                        link2.getCreatedDate().toLocalDate(), link2.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크3 = new LinkResponse(
                new LinkData(link3.getId(), 디우_응답, link3.getLinkUrl(), link3.getDescription(),
                        link3.getCreatedDate().toLocalDate(), link3.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크4 = new LinkResponse(
                new LinkData(link4.getId(), 베루스_응답, link4.getLinkUrl(), link4.getDescription(),
                        link4.getCreatedDate().toLocalDate(), link4.getLastModifiedDate().toLocalDate()));

        linkResponses = List.of(링크1, 링크2, 링크3, 링크4);
    }

    @DisplayName("링크 공유글 전체 목록 조회를 할 수 있다.")
    @Test
    void getLinks() {
        final ResponseEntity<LinksResponse> links = sut.getLinks(짱구_깃허브_아이디, javaStudy.getId(), PageRequest.of(0, 5));

        assertAll(
                () -> assertThat(links.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(links.getBody()).isNotNull(),
                () -> assertThat(links.getBody().isHasNext()).isFalse(),
                () -> assertThat(links.getBody().getLinks())
                        .containsExactlyInAnyOrderElementsOf(linkResponses)
        );
    }

    @DisplayName("스터디에 참여하지 않은 회원은 링크 공유글을 조회할 수 없다.")
    @Test
    void getLinksByNotParticipatedMember() {
        assertThatThrownBy(() -> sut.getLinks(병민_깃허브_아이디, javaStudy.getId(), PageRequest.of(0, 5)))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}
