package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.LinkData;
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

    public LinkResponse(final LinkData linkData) {
        this(
                linkData.getId(), new AuthorResponse(linkData.getMemberData()),
                linkData.getLinkUrl(), linkData.getDescription(),
                linkData.getCreatedDate(), linkData.getLastModifiedDate()
        );
    }
}
