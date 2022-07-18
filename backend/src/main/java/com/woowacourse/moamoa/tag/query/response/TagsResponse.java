package com.woowacourse.moamoa.tag.query.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagsResponse {

    private final List<TagResponse> tags;
}
