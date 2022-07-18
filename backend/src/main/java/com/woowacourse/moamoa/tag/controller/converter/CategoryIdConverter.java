package com.woowacourse.moamoa.tag.controller.converter;

import com.woowacourse.moamoa.tag.domain.CategoryId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdConverter implements Converter<String, CategoryId> {

    @Override
    public CategoryId convert(final String source) {
        return source.isBlank() ? CategoryId.empty() : new CategoryId(Long.valueOf(source));
    }
}
