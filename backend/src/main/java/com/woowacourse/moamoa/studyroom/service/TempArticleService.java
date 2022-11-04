package com.woowacourse.moamoa.studyroom.service;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.domain.article.Content;
import com.woowacourse.moamoa.studyroom.domain.article.TempArticle;
import com.woowacourse.moamoa.studyroom.domain.article.repository.ArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.TempArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.TempArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.TempArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UnViewableException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.CreatedArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.TempArticlesResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TempArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final ArticleRepository articleRepository;
    private final TempArticleRepository tempArticleRepository;
    private final TempArticleDao tempArticleDao;

    public TempArticleService(
            final StudyRoomRepository studyRoomRepository,
            final ArticleRepository articleRepository,
            final TempArticleRepository tempArticleRepository,
            final TempArticleDao tempArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.articleRepository = articleRepository;
        this.tempArticleRepository = tempArticleRepository;
        this.tempArticleDao = tempArticleDao;
    }

    @Transactional
    public CreatedTempArticleIdResponse createTempArticle(
            final Long memberId, final Long studyId, final ArticleRequest request, final ArticleType articleType
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));
        final Accessor accessor = new Accessor(memberId, studyId);
        final Content content = new Content(request.getTitle(), request.getContent());
        final TempArticle article = TempArticle.create(content, studyRoom, accessor, articleType);

        final Long newArticleId = tempArticleRepository.save(article).getId();
        return new CreatedTempArticleIdResponse(newArticleId);
    }

    public TempArticleResponse getTempArticle(
            final Long memberId, final Long studyId, final Long articleId, final ArticleType articleType
    ) {
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId, articleType));

        if (tempArticle.isForbiddenAccessor(new Accessor(memberId, studyId))) {
            throw new UnViewableException(articleId, new Accessor(memberId, studyId));
        }

        final TempArticleData tempArticleData = tempArticleDao.getById(articleId, articleType)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId, articleType));
        return new TempArticleResponse(tempArticleData);
    }

    public TempArticlesResponse getTempArticles(
            final Long memberId, final Pageable pageable, final ArticleType articleType
    ) {
        final Page<TempArticleData> page = tempArticleDao.getAll(memberId, articleType, pageable);
        final List<TempArticleResponse> content = page.getContent().stream()
                .map(TempArticleResponse::new)
                .collect(Collectors.toList());

        if (content.isEmpty()) {
            return new TempArticlesResponse(Collections.emptyList(), 0, 0, 0);
        }
        return new TempArticlesResponse(content, page.getNumber(), page.getTotalPages() - 1, page.getTotalElements());
    }

    @Transactional
    public void updateTempArticle(
            final Long memberId, final Long studyId, final Long articleId,
            final ArticleRequest request, final ArticleType articleType
    ) {
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId, articleType));
        final Accessor accessor = new Accessor(memberId, studyId);
        tempArticle.update(accessor, new Content(request.getTitle(), request.getContent()));
    }

    @Transactional
    public void deleteTempArticle(
            final Long memberId, final Long studyId, final Long articleId, final ArticleType articleType
    ) {
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId, articleType));

        final Accessor accessor = new Accessor(memberId, studyId);
        if (tempArticle.isForbiddenAccessor(accessor)) {
            throw UneditableException.forTempArticle(articleId, accessor);
        }

        tempArticleRepository.delete(tempArticle);
    }

    @Transactional
    public CreatedArticleIdResponse publishTempArticle(
            final Long memberId, final Long studyId, final Long articleId, final ArticleType articleType,
            final ArticleRequest request
    ) {
        final Accessor accessor = new Accessor(memberId, studyId);
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId, articleType));

        tempArticle.update(accessor, request.createContent());
        final Article publishedArticle = tempArticle.publish(accessor);

        tempArticleRepository.delete(tempArticle);
        final Article newArticle = articleRepository.save(publishedArticle);
        return new CreatedArticleIdResponse(newArticle.getId());
    }
}
