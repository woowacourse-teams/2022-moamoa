package com.woowacourse.moamoa.referenceroom.service.response;

import com.woowacourse.moamoa.referenceroom.query.data.LinkData;
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
public class LinkResponse {

    private Long id;

    private AuthorResponse author;

    private String linkUrl;

    private String description;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    public LinkResponse(final LinkData linkData) {
        this(
                linkData.getId(), new AuthorResponse(linkData.getMember()),
                linkData.getLinkUrl(), linkData.getDescription(),
                linkData.getCreatedDate(), linkData.getLastModifiedDate()
        );
    }
}
