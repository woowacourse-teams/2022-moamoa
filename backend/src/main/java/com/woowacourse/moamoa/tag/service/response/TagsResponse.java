package com.woowacourse.moamoa.tag.service.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagsResponse {

    private List<TagResponse> tags;
}
