package com.woowacourse.moamoa.studyroom.service.response.temp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreatedTempArticleIdResponse {

    private long draftArticleId;

    public CreatedTempArticleIdResponse(final long draftArticleId) {
        this.draftArticleId = draftArticleId;
    }
}
