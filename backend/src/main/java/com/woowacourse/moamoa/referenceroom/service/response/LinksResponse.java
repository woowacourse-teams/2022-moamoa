package com.woowacourse.moamoa.referenceroom.service.response;

import com.woowacourse.moamoa.referenceroom.query.data.LinkData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LinksResponse {

    private List<LinkResponse> links;

    private boolean hasNext;

    public LinksResponse(final List<LinkData> linkData, final boolean hasNext) {
        this.links = getLinkResponses(linkData);
        this.hasNext = hasNext;
    }

    private List<LinkResponse> getLinkResponses(final List<LinkData> linkData) {
        return linkData.stream()
                .map(LinkResponse::new)
                .collect(Collectors.toList());
    }
}
