package com.woowacourse.moamoa.studyroom.service;

import static com.woowacourse.moamoa.studyroom.domain.article.ArticleType.NOTICE;

import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;
import com.woowacourse.moamoa.studyroom.domain.article.TempArticle;
import com.woowacourse.moamoa.studyroom.domain.article.repository.TempArticleRepository;
import com.woowacourse.moamoa.studyroom.domain.studyroom.StudyRoom;
import com.woowacourse.moamoa.studyroom.domain.studyroom.repository.StudyRoomRepository;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TempArticleService {

    private final StudyRoomRepository studyRoomRepository;
    private final TempArticleRepository tempArticleRepository;

    public TempArticleService(
            final StudyRoomRepository studyRoomRepository,
            final TempArticleRepository articleRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.tempArticleRepository = articleRepository;
    }

    @Transactional
    public CreatedTempArticleIdResponse createTempArticle(
            final Long authorId, final Long studyId, final ArticleRequest request
    ) {
        final StudyRoom studyRoom = studyRoomRepository.findByStudyId(studyId)
                .orElseThrow(() -> new StudyNotFoundException(studyId));

        final Accessor accessor = new Accessor(authorId, studyId);
        final TempArticle article = TempArticle.create(
                studyRoom, accessor, request.getTitle(), request.getContent(), NOTICE
        );
        final Long newArticleId = tempArticleRepository.save(article).getId();
        return new CreatedTempArticleIdResponse(newArticleId);
    }
}
