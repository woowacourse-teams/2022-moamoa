package com.woowacourse.moamoa.studyroom.service.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreatedArticleIdResponse {

    private Long articleId;

    public CreatedArticleIdResponse(final Long articleId) {
        this.articleId = articleId;
    }
}
