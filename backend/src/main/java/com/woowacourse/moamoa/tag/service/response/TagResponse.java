package com.woowacourse.moamoa.tag.service.response;

import com.woowacourse.moamoa.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagResponse {

    private Long id;
    private String tagName;

    public TagResponse(Tag tag) {
        this(tag.getId(), tag.getName());
    }
}
