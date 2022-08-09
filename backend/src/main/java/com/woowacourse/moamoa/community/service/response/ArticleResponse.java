package com.woowacourse.moamoa.community.service.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

    private Long articleId;
    private AuthorResponse authorResponse;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
