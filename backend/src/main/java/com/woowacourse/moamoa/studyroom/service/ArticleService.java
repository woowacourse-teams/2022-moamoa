package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.repository.article.ArticleRepositoryFactory;
import com.woowacourse.moamoa.studyroom.domain.repository.studyroom.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.LinkArticleDao;
import com.woowacourse.moamoa.studyroom.query.PostArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import com.woowacourse.moamoa.studyroom.query.data.PostArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.ArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UneditableArticleException;
import com.woowacourse.moamoa.studyroom.service.exception.UnviewableArticleException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummaryResponse;
import com.woowacourse.moamoa.studyroom.service.response.LinkArticlesResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final ArticleRepositoryFactory articleRepositoryFactory;
    private final PostArticleDao postArticleDao;
    private LinkArticleDao linkArticleDao;

    @Autowired
    public ArticleService(final StudyRoomRepository studyRoomRepository,
                          final ArticleRepositoryFactory articleRepositoryFactory,
                          final PostArticleDao postArticleDao,
                          final LinkArticleDao linkArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepositoryFactory = articleRepositoryFactory;
        this.postArticleDao = postArticleDao;
        this.linkArticleDao = linkArticleDao;
    }


    public ArticleService(final StudyRoomRepository studyRoomRepository,
                          final ArticleRepositoryFactory articleRepositoryFactory,
                          final PostArticleDao postArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepositoryFactory = articleRepositoryFactory;
        this.postArticleDao = postArticleDao;
    }

    @Transactional
    public Article createArticle(final Long memberId, final Long studyId,
                                 final ArticleRequest request, final ArticleType articleType) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);
        final Accessor accessor = new Accessor(memberId, studyId);
        final Article article = studyRoom.writeArticle(accessor, request.toContent(), articleType);
        final ArticleRepository<Article> repository = articleRepositoryFactory.getRepository(articleType);
        return repository.save(article);
    }

    public ArticleResponse getPostArticle(final Long memberId, final Long studyId, final Long articleId,
                                          final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isViewableBy(new Accessor(memberId, studyId))) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final PostArticleData data = postArticleDao.getById(articleId, type)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        return new ArticleResponse(data);
    }

    @Transactional
    public void deleteArticle(final Long memberId, final Long studyId, final Long articleId, final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (!article.isEditableBy(new Accessor(memberId, studyId))) {
            throw new UneditableArticleException();
        }

        articleRepositoryFactory.getRepository(type).deleteById(articleId);
    }

    public ArticleSummariesResponse getPostArticles(final Long memberId, final Long studyId, final Pageable pageable,
                                                    final ArticleType type) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!studyRoom.isPermittedAccessor(new Accessor(memberId, studyId))) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final Page<PostArticleData> page = postArticleDao.getAllByStudyId(studyId, pageable, type);

        final List<ArticleSummaryResponse> articles = page.getContent().stream()
                .map(ArticleSummaryResponse::new)
                .collect(Collectors.toList());

        return new ArticleSummariesResponse(articles, page.getNumber(), page.getTotalPages() - 1,
                page.getTotalElements());
    }

    public LinkArticlesResponse getLinkArticles(final Long memberId, final Long studyId, final Pageable pageable) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!studyRoom.isPermittedAccessor(new Accessor(memberId, studyId))) {
            throw new UnviewableArticleException(studyId, memberId);
        }

        final Slice<LinkArticleData> slice = linkArticleDao.findAllByStudyId(studyId, pageable);
        return new LinkArticlesResponse(slice.getContent(), slice.hasNext());
    }

    @Transactional
    public void updateArticle(final Long memberId, final Long studyId, final Long articleId,
                              final ArticleRequest request, final ArticleType type) {
        final Article article = articleRepositoryFactory.getRepository(type)
                .findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        final Accessor accessor = new Accessor(memberId, studyId);

        if (!article.isEditableBy(accessor)) {
            throw new UneditableArticleException();
        }

        article.update(accessor, request.toContent());
    }
}
