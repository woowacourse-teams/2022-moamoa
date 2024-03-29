package com.woowacourse.moamoa.studyroom.domain.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.studyroom.domain.Accessor;

public class UneditableException extends BadRequestException {

    public UneditableException(final String message) {
        super(message);
    }

    public static UneditableException forArticle(final Long studyId, final Accessor accessor, final String typeName) {
        final String message = String.format("스터디[%d]에 접근자[%s]가 %s의 게시글을 수정/삭제할 수 없습니다.", studyId, accessor, typeName);
        return new UneditableException(message);
    }

    public static UneditableException forTempArticle(final Long articleId, final Accessor accessor) {
        final String message = String.format("임시 게시글[%d]에 접근자[%s]가 수정/삭제할 수 없습니다.", articleId, accessor);
        return new UneditableException(message);
    }
}
