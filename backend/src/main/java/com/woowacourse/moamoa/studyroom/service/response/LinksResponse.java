package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
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

    public LinksResponse(final List<LinkArticleData> linkArticleData, final boolean hasNext) {
        this.links = getLinkResponses(linkArticleData);
        this.hasNext = hasNext;
    }

    private List<LinkResponse> getLinkResponses(final List<LinkArticleData> linkArticleData) {
        return linkArticleData.stream()
                .map(LinkResponse::new)
                .collect(Collectors.toList());
    }
}
