package com.woowacourse.moamoa.tag.controller.converter;

import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryIdConverter implements Converter<String, Optional<Long>> {

    @Override
    public Optional<Long> convert(final String source) {
        return source.isBlank() ? Optional.empty() : Optional.of(Long.valueOf(source));
    }
}
