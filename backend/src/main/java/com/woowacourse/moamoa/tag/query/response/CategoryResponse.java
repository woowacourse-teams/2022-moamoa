package com.woowacourse.moamoa.tag.query.response;

import com.woowacourse.moamoa.tag.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private final Long id;
    private final String name;

    public CategoryResponse(final Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
