package com.woowacourse.moamoa.community.service.exception;

import com.woowacourse.moamoa.common.exception.BadRequestException;

public class UneditableArticleException extends BadRequestException {

    public UneditableArticleException() {
        super("");
    }
}
