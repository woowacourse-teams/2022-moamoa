package com.woowacourse.moamoa.tag.controller.converter;

import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdConverter implements Converter<String, CategoryIdRequest> {

    @Override
    public CategoryIdRequest convert(final String source) {
        return source.isBlank() ? CategoryIdRequest.empty() : new CategoryIdRequest(Long.parseLong(source));
    }
}
