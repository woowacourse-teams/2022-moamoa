package com.woowacourse.moamoa.community.service.response;

import java.time.LocalDate;

public class ArticleSummaryResponse {

    private long id;
    private AuthorResponse author;
    private String title;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

    public ArticleSummaryResponse(final long id, final AuthorResponse author, final String title,
                                  final LocalDate createdDate,
                                  final LocalDate lastModifiedDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
