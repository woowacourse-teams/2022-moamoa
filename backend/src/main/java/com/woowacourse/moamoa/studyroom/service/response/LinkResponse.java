package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LinkResponse {

    private Long id;

    private AuthorResponse author;

    private String linkUrl;

    private String description;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    public LinkResponse(final LinkArticleData linkArticleData) {
        this(
                linkArticleData.getId(), new AuthorResponse(linkArticleData.getMemberData()),
                linkArticleData.getLinkUrl(), linkArticleData.getDescription(),
                linkArticleData.getCreatedDate(), linkArticleData.getLastModifiedDate()
        );
    }
}
