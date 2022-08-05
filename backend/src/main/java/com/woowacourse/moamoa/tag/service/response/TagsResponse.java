package com.woowacourse.moamoa.tag.service.response;

import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagsResponse {

    private final List<TagData> tags;
}
