package com.woowacourse.moamoa.tag.query.response;

import com.woowacourse.moamoa.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TagResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final CategoryResponse category;

    public TagResponse(final Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.description = tag.getDescription();
        this.category = new CategoryResponse(tag.getCategory());
    }
}
