package com.woowacourse.moamoa.studyroom.controller;

import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.그린론_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.디우_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.베루스_응답;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.병민;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_깃허브_아이디;
import static com.woowacourse.moamoa.fixtures.MemberFixtures.짱구_응답;
import static com.woowacourse.moamoa.fixtures.StudyFixtures.자바_스터디_신청서;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.studyroom.domain.article.LinkArticle;
import com.woowacourse.moamoa.studyroom.domain.repository.article.LinkArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkData;
import com.woowacourse.moamoa.studyroom.service.LinkArticleService;
import com.woowacourse.moamoa.studyroom.service.SearchingLinkArticleService;
import com.woowacourse.moamoa.studyroom.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.LinkResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class SearchingLinkArticleControllerTest {

    @Autowired
    private LinkDao linkDao;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private LinkArticleRepository linkArticleRepository;

    @Autowired
    private EntityManager entityManager;

    private SearchingLinkArticleController sut;

    private Study javaStudy;

    private List<LinkResponse> linkResponses;
    private Member jjanggu;
    private Member greenlawn;
    private Member dwoo;
    private Member verus;
    private Member byeongmin;

    @BeforeEach
    void setUp() {
        sut = new SearchingLinkArticleController(
                new SearchingLinkArticleService(linkDao, memberRepository, studyRepository));

        // 사용자 추가
        jjanggu = memberRepository.save(짱구());
        greenlawn = memberRepository.save(그린론());
        dwoo = memberRepository.save(디우());
        verus = memberRepository.save(베루스());
        byeongmin = memberRepository.save(병민());

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        StudyRequest javaStudyRequest = 자바_스터디_신청서(startDate);

        javaStudy = studyService.createStudy(짱구_깃허브_아이디, javaStudyRequest);

        StudyParticipantService participantService = new StudyParticipantService(memberRepository, studyRepository);
        participantService.participateStudy(greenlawn.getId(), javaStudy.getId());
        participantService.participateStudy(dwoo.getId(), javaStudy.getId());
        participantService.participateStudy(verus.getId(), javaStudy.getId());

        // 링크 공유 추가
        final LinkArticleService linkService = new LinkArticleService(studyRoomRepository, memberRepository,
                studyRepository, linkArticleRepository);

        final LinkArticleRequest request1 = new LinkArticleRequest("https://github.com/sc0116", "짱구 링크.");
        final LinkArticleRequest request2 = new LinkArticleRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final LinkArticleRequest request3 = new LinkArticleRequest("https://github.com/tco0427", "디우 링크.");
        final LinkArticleRequest request4 = new LinkArticleRequest("https://github.com/wilgur513", "베루스 링크.");

        final LinkArticle linkArticle1 = linkService.createLink(jjanggu.getId(), javaStudy.getId(), request1);
        final LinkArticle linkArticle2 = linkService.createLink(greenlawn.getId(), javaStudy.getId(), request2);
        final LinkArticle linkArticle3 = linkService.createLink(dwoo.getId(), javaStudy.getId(), request3);
        final LinkArticle linkArticle4 = linkService.createLink(verus.getId(), javaStudy.getId(), request4);

        entityManager.flush();
        entityManager.clear();

        final LinkResponse 링크1 = new LinkResponse(
                new LinkData(linkArticle1.getId(), 짱구_응답, linkArticle1.getLinkUrl(), linkArticle1.getDescription(),
                        linkArticle1.getCreatedDate().toLocalDate(), linkArticle1.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크2 = new LinkResponse(
                new LinkData(linkArticle2.getId(), 그린론_응답, linkArticle2.getLinkUrl(), linkArticle2.getDescription(),
                        linkArticle2.getCreatedDate().toLocalDate(), linkArticle2.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크3 = new LinkResponse(
                new LinkData(linkArticle3.getId(), 디우_응답, linkArticle3.getLinkUrl(), linkArticle3.getDescription(),
                        linkArticle3.getCreatedDate().toLocalDate(), linkArticle3.getLastModifiedDate().toLocalDate()));
        final LinkResponse 링크4 = new LinkResponse(
                new LinkData(linkArticle4.getId(), 베루스_응답, linkArticle4.getLinkUrl(), linkArticle4.getDescription(),
                        linkArticle4.getCreatedDate().toLocalDate(), linkArticle4.getLastModifiedDate().toLocalDate()));

        linkResponses = List.of(링크1, 링크2, 링크3, 링크4);
    }

    @DisplayName("링크 공유글 전체 목록 조회를 할 수 있다.")
    @Test
    void getLinks() {
        final ResponseEntity<LinksResponse> links = sut.getLinks(jjanggu.getId(), javaStudy.getId(), PageRequest.of(0, 5));

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
        final Long javaStudyId = javaStudy.getId();
        final PageRequest pageRequest = PageRequest.of(0, 5);

        assertThatThrownBy(() -> sut.getLinks(byeongmin.getId(), javaStudyId, pageRequest))
                .isInstanceOf(NotParticipatedMemberException.class);
    }
}
