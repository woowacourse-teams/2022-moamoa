package com.woowacourse.moamoa.studyroom.service.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
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
