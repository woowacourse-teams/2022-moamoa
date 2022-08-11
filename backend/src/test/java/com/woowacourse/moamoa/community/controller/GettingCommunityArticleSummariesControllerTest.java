package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.service.CommunityArticleService;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequestBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@RepositoryTest
public class GettingCommunityArticleSummariesControllerTest {

    CreatingStudyRequestBuilder javaStudyRequest = new CreatingStudyRequestBuilder()
            .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개");

    private CommunityArticleService communityArticleService;

    private StudyService studyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CommunityArticleRepository communityArticleRepository;

    @Autowired
    private CommunityArticleDao communityArticleDao;

    @BeforeEach
    void setUp() {
        communityArticleService = new CommunityArticleService(memberRepository, studyRepository,
                communityArticleRepository, communityArticleDao);
        studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());
    }

    @DisplayName("스터디 커뮤니티 글 목록을 조회한다.")
    @Test
    void getCommunityArticles() {
        final Member 그린론 = memberRepository.save(new Member(1L, "그린론", "http://image", "http://profile"));

        final Study study = studyService.createStudy(그린론.getGithubId(),
                javaStudyRequest.startDate(LocalDate.now()).build());

        communityArticleService.createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("자바글1 제목", "글 내용"));
        communityArticleService.createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("자바글2 제목", "글 내용"));
        communityArticleService.createArticle(그린론.getGithubId(), study.getId(), new ArticleRequest("자바글3 제목", "글 내용"));

        CommunityArticleController sut = new CommunityArticleController(communityArticleService);

        sut.getArticles(그린론.getGithubId(), study.getId(), PageRequest.of(0, 3));
    }
}
