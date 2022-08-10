package com.woowacourse.moamoa.referenceroom.service.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LinksResponse {

    private List<LinkResponse> links;
    private boolean hasNext;
}
