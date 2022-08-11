package com.woowacourse.moamoa.community.service;

import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.query.CommunityArticleDao;
import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import com.woowacourse.moamoa.community.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.community.service.exception.NotArticleAuthorException;
import com.woowacourse.moamoa.community.service.exception.NotRelatedArticleException;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.exception.NotParticipatedMemberException;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommunityArticleService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final CommunityArticleRepository communityArticleRepository;
    private final CommunityArticleDao communityArticleDao;

    public CommunityArticleService(final MemberRepository memberRepository,
                                   final StudyRepository studyRepository,
                                   final CommunityArticleRepository communityArticleRepository,
                                   final CommunityArticleDao communityArticleDao) {
        this.memberRepository = memberRepository;
        this.studyRepository = studyRepository;
        this.communityArticleRepository = communityArticleRepository;
        this.communityArticleDao = communityArticleDao;
    }

    @Transactional
    public CommunityArticle createArticle(final Long githubId, final Long studyId,
                                          final ArticleRequest request) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        return communityArticleRepository.save(CommunityArticle.write(member, study, request));
    }

    public ArticleResponse getArticle(final Long githubId, final Long studyId, final Long articleId) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        if (!article.isBelongTo(study.getId())) {
            throw new NotRelatedArticleException(study.getId(), article.getId());
        }

        final CommunityArticleData data = communityArticleDao.getById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long githubId, final Long studyId, final Long articleId) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        if (!article.isBelongTo(study.getId())) {
            throw new NotRelatedArticleException(study.getId(), article.getId());
        }

        if (!article.isAuthor(member.getId())) {
            throw new NotArticleAuthorException(article.getId(), member.getId());
        }

        communityArticleRepository.deleteById(articleId);
    }

    public ArticleSummariesResponse getArticles(final Long githubId, final Long studyId, final Pageable pageable) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        final Page<CommunityArticleData> page = communityArticleDao.getAllByStudyId(studyId, pageable);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1, page.getTotalElements());
    }

    @Transactional
    public void updateArticle(final Long githubId, final Long studyId, final Long articleId,
                              final ArticleRequest request) {
        final Member member = memberRepository.findByGithubId(githubId).orElseThrow(MemberNotFoundException::new);
        final Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        final CommunityArticle article = communityArticleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!study.isParticipant(member.getId())) {
            throw new NotParticipatedMemberException();
        }

        if (!article.isBelongTo(study.getId())) {
            throw new NotRelatedArticleException(study.getId(), article.getId());
        }

        if (!article.isAuthor(member.getId())) {
            throw new NotArticleAuthorException(article.getId(), member.getId());
        }

        article.update(request.getTitle(), request.getContent());
    }
}
