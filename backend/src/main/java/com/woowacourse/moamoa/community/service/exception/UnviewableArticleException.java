package com.woowacourse.moamoa.community.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UnviewableArticleException extends BadRequestException {

    public UnviewableArticleException(final Long studyId, final Long articleId) {
        super("스터디[" + studyId + "] 에 작성된 게시글[" + articleId + "]을 볼 수 없습니다.");
    }
}
