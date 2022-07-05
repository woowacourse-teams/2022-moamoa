package com.woowacourse.moamoa.config;

import com.woowacourse.moamoa.exception.InvalidFormatException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableVerificationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String page = webRequest.getParameter("page");
        final String size = webRequest.getParameter("size");

        if ((isNotNull(page) && isNumeric(page)) || (isNotNull(size) && isNumeric(size))) {
            throw new InvalidFormatException();
        }

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private boolean isNotNull(String text) {
        return text != null;
    }

    private boolean isNumeric(String text) {
        return text.chars()
                .allMatch(Character::isDigit);
    }
}
