package com.woowacourse.moamoa.studyroom.controller.converter;

import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleTypeConverter implements Converter<String, ArticleType> {

    @Override
    public ArticleType convert(final String source) {
        return ArticleType.valueOf(source.toUpperCase());
    }
}
