package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ArticleSummaryResponse {

    private long id;
    private AuthorResponse author;
    private String title;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

    public ArticleSummaryResponse(ArticleData data) {
        this.id = data.getId();
        this.author = new AuthorResponse(data.getMemberData());
        this.title = data.getTitle();
        this.createdDate = data.getCreatedDate();
        this.lastModifiedDate = data.getLastModifiedDate();
    }

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
