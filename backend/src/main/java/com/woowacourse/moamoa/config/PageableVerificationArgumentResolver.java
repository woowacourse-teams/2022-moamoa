package com.woowacourse.moamoa.config;

import com.woowacourse.moamoa.exception.InvalidFormatException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableVerificationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final int MINIMUM_PAGE = 0;
    private static final int MINIMUM_SIZE = 1;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String page = webRequest.getParameter("page");
        final String size = webRequest.getParameter("size");

        validatePageAndSize(page, size);

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private void validatePageAndSize(String page, String size) {
        if (page == null && size == null) {
            return;
        }

        if (page == null || size == null) {
            throw new InvalidFormatException();
        }

        if (isInvalidPage(page) || isInvalidSize(size)) {
            throw new InvalidFormatException();
        }
    }

    private boolean isInvalidPage(String page) {
        return !isNumeric(page) || (Integer.parseInt(page) < MINIMUM_PAGE);
    }

    private boolean isInvalidSize(String size) {
        return !isNumeric(size) || (Integer.parseInt(size) < MINIMUM_SIZE);
    }

    private boolean isNumeric(String text) {
        for (char character : text.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }
}
