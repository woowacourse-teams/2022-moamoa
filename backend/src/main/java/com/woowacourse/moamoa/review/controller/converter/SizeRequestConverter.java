package com.woowacourse.moamoa.review.controller.converter;

import com.woowacourse.moamoa.review.service.request.SizeRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SizeRequestConverter implements Converter<String, SizeRequest> {

    @Override
    public SizeRequest convert(final String source) {
        return source.isBlank() ? SizeRequest.empty() : new SizeRequest(Integer.parseInt(source));
    }
}
