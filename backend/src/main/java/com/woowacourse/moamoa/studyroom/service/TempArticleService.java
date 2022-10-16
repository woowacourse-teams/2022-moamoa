package com.woowacourse.moamoa.studyroom.service;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.TempArticle;
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.exception.UneditableException;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.query.TempArticleDao;
import com.woowacourse.moamoa.studyroom.query.data.TempArticleData;
import com.woowacourse.moamoa.studyroom.service.exception.TempArticleNotFoundException;
import com.woowacourse.moamoa.studyroom.service.exception.UnviewableException;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TempArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final TempArticleRepository tempArticleRepository;
    private final TempArticleDao tempArticleDao;

    public TempArticleService(
            final StudyRoomRepository studyRoomRepository,
            final TempArticleRepository articleRepository,
            final TempArticleDao tempArticleDao) {
        this.studyRoomRepository = studyRoomRepository;
        this.tempArticleRepository = articleRepository;
        this.tempArticleDao = tempArticleDao;
    }

    @Transactional
    public CreatedTempArticleIdResponse createTempArticle(
            final Long memberId, final Long studyId, final ArticleRequest request
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));

        final Accessor accessor = new Accessor(memberId, studyId);
        final TempArticle article = TempArticle.create(
                studyRoom, accessor, request.getTitle(), request.getContent(), NOTICE
        );
        final Long newArticleId = tempArticleRepository.save(article).getId();
        return new CreatedTempArticleIdResponse(newArticleId);
    }

    public TempArticleResponse getTempArticle(final Long memberId, final Long studyId, final Long articleId) {
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId));

        if (tempArticle.isForbiddenAccessor(new Accessor(memberId, studyId))) {
            throw new UnviewableException(articleId, new Accessor(memberId, studyId));
        }

        final TempArticleData tempArticleData = tempArticleDao.getById(articleId).orElseThrow();
        return new TempArticleResponse(tempArticleData);
    }

    @Transactional
    public void deleteTempArticle(final Long memberId, final Long studyId, final Long articleId) {
        final TempArticle tempArticle = tempArticleRepository.findById(articleId)
                .orElseThrow(() -> new TempArticleNotFoundException(articleId));

        if (tempArticle.isForbiddenAccessor(new Accessor(memberId, studyId))) {
            throw UneditableException.forTempArticle(articleId, new Accessor(memberId, studyId));
        }

        tempArticleRepository.delete(tempArticle);
    }
}
