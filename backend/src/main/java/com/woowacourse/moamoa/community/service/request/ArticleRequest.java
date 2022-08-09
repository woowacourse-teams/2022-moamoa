package com.woowacourse.moamoa.community.service.request;

import lombok.Getter;

@Getter
public class ArticleRequest {

    private String title;
    private String content;

    public ArticleRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
