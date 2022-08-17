package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class NotRelatedArticleException extends BadRequestException {

    public NotRelatedArticleException(final Long studyId, final Long articleId) {
        super("스터디[" + studyId + "] 에 작성된 게시글[" + articleId + "] 가 없습니다.");
    }
}
