package com.woowacourse.moamoa.studyroom.service.response;

import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LinkArticlesResponse {

    private List<LinkArticleResponse> links;

    private boolean hasNext;

    public LinkArticlesResponse(final List<LinkArticleData> linkData, final boolean hasNext) {
        this.links = getLinkResponses(linkData);
        this.hasNext = hasNext;
    }

    private List<LinkArticleResponse> getLinkResponses(final List<LinkArticleData> linkData) {
        return linkData.stream()
                .map(LinkArticleResponse::new)
                .collect(Collectors.toList());
    }
}
