package com.woowacourse.moamoa.community.service.response;

import java.util.List;

public class ArticleSummariesResponse {

    private List<ArticleSummaryResponse> articles;
    private long currentPage;
    private long lastPage;
    private long totalCount;

    public ArticleSummariesResponse(
            final List<ArticleSummaryResponse> articles, final long currentPage, final long lastPage,
            final long totalCount) {
        this.articles = articles;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.totalCount = totalCount;
    }
}
