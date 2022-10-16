package com.woowacourse.moamoa.studyroom.service.response.temp;

import lombok.Getter;

@Getter
public class CreatedTempArticleIdResponse {

    private long draftArticleId;

    public CreatedTempArticleIdResponse(final long draftArticleId) {
        this.draftArticleId = draftArticleId;
    }
}
