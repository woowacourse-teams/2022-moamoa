package com.woowacourse.moamoa.studyroom.controller.converter;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleTypeConverter implements Converter<String, ArticleType> {

    Logger logger = LoggerFactory.getLogger(ArticleTypeConverter.class);

    @Override
    public ArticleType convert(final String source) {
        logger.info("{}", ArticleType.valueOf(source.toUpperCase()));
        return ArticleType.valueOf(source.toUpperCase());
    }
}
