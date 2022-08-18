package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleResponse {

    private Long id;
    private AuthorResponse author;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

    public ArticleResponse(ArticleData data) {
        this(data.getId(), new AuthorResponse(data.getMemberData()), data.getTitle(), data.getContent(),
                data.getCreatedDate(), data.getLastModifiedDate());
    }
}
