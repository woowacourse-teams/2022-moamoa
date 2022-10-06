package com.woowacourse.moamoa.studyroom.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;

public class UneditableException extends BadRequestException {

    public UneditableException(final Long studyId, final Accessor accessor, final String typeName) {
        super(String.format("스터디[%d]에 접근자[%s]가 %s의 게시글을 수정/삭제할 수 없습니다.", studyId, accessor, typeName));
    }
}
