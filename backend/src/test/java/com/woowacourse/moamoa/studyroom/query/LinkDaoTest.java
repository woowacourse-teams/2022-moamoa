package com.woowacourse.moamoa.studyroom.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
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
    private StudyRoomRepository studyRoomRepository;

    @Autowired
    private ArticleRepositoryFactory articleRepositoryFactory;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private LinkArticleDao linkArticleDao;

    @Autowired
    private PostArticleDao postArticleDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LinkArticleDao sut;

    private Study javaStudy;

    private List<LinkArticleData> linkData;

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
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        javaStudy = createStudyService.createStudy(JJANGGU.getGithubId(), javaStudyRequest);
        createStudyService.participateStudy(GREENLAWN.getGithubId(), javaStudy.getId());
        createStudyService.participateStudy(DWOO.getGithubId(), javaStudy.getId());
        createStudyService.participateStudy(VERUS.getGithubId(), javaStudy.getId());

        // 링크 공유 추가
        final ArticleService articleService = new ArticleService(studyRoomRepository, articleRepositoryFactory,
                postArticleDao, linkArticleDao);

        final LinkArticleRequest request1 = new LinkArticleRequest("https://github.com/sc0116", "짱구 링크.");
        final LinkArticleRequest request2 = new LinkArticleRequest("https://github.com/jaejae-yoo", "그린론 링크.");
        final LinkArticleRequest request3 = new LinkArticleRequest("https://github.com/tco0427", "디우 링크.");
        final LinkArticleRequest request4 = new LinkArticleRequest("https://github.com/wilgur513", "베루스 링크.");

        final Article link1 = articleService.createArticle(jjanggu.getId(), javaStudy.getId(), request1, ArticleType.LINK);
        final Article link2 = articleService.createArticle(greenlawn.getId(), javaStudy.getId(), request2, ArticleType.LINK);
        final Article link3 = articleService.createArticle(dwoo.getId(), javaStudy.getId(), request3, ArticleType.LINK);
        final Article link4 = articleService.createArticle(verus.getId(), javaStudy.getId(), request4, ArticleType.LINK);

        entityManager.flush();
        entityManager.clear();

        final LinkArticleData 링크1 = new LinkArticleData(link1.getId(), JJANGGU, request1.getLinkUrl(), request1.getDescription(),
                link1.getCreatedDate().toLocalDate(), link1.getLastModifiedDate().toLocalDate());
        final LinkArticleData 링크2 = new LinkArticleData(link2.getId(), GREENLAWN, request2.getLinkUrl(), request2.getDescription(),
                link2.getCreatedDate().toLocalDate(), link2.getLastModifiedDate().toLocalDate());
        final LinkArticleData 링크3 = new LinkArticleData(link3.getId(), DWOO, request3.getLinkUrl(), request3.getDescription(),
                link3.getCreatedDate().toLocalDate(), link3.getLastModifiedDate().toLocalDate());
        final LinkArticleData 링크4 = new LinkArticleData(link4.getId(), VERUS, request4.getLinkUrl(), request4.getDescription(),
                link4.getCreatedDate().toLocalDate(), link4.getLastModifiedDate().toLocalDate());

        linkData = List.of(링크1, 링크2, 링크3, 링크4);
    }

    private Member toMember(final MemberData data) {
        return new Member(data.getGithubId(), data.getUsername(), data.getImageUrl(), data.getProfileUrl());
    }

    @DisplayName("스터디 ID로 링크 공유글을 조회한다.")
    @Test
    void getLinks() {
        final Slice<LinkArticleData> links = sut.findAllByStudyId(javaStudy.getId(), PageRequest.of(0, 5));

        assertAll(
                () -> assertThat(links.hasNext()).isFalse(),
                () -> assertThat(links.getContent())
                        .containsExactlyInAnyOrderElementsOf(linkData)
        );
    }
}
