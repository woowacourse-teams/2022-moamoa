package com.woowacourse.moamoa.studyroom.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UneditableArticleException extends BadRequestException {

    public UneditableArticleException() {
        super("");
    }
}
