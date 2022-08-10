package com.woowacourse.moamoa.community.service.response;

import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleResponse {

    private Long articleId;
    private AuthorResponse authorResponse;
    private String title;
    private String content;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

    public ArticleResponse(CommunityArticleData data) {
        this(data.getId(), new AuthorResponse(data.getMemberData()), data.getTitle(), data.getContent(),
                data.getCreatedDate(), data.getLastModifiedDate());
    }
}
