package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;

public class UnviewableException extends UnauthorizedException {

    public UnviewableException(final Long articleId, final Accessor accessor) {
        super(String.format("임시글[%d]를 접근자[%s]가 조회할 수 없습니다.", articleId, accessor));
    }
}
