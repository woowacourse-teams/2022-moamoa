package com.woowacourse.moamoa.common.config;

import com.woowacourse.moamoa.common.exception.InvalidFormatException;
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
    public boolean supportsParameter(final MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(final MethodParameter methodParameter,
                                    final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest,
                                    final WebDataBinderFactory binderFactory
    ) {
        final String page = webRequest.getParameter("page");
        final String size = webRequest.getParameter("size");

        if (isInvalidPageAndSize(page, size)) {
            throw new InvalidFormatException();
        }

        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private boolean isInvalidPageAndSize(final String page, final String size) {
        if (page == null && size == null) {
            return false;
        }

        if (page == null || size == null) {
            return true;
        }

        return isInvalidPage(page) || isInvalidSize(size);
    }

    private boolean isInvalidPage(final String page) {
        return !isNumeric(page) || (Integer.parseInt(page) < MINIMUM_PAGE);
    }

    private boolean isInvalidSize(final String size) {
        return !isNumeric(size) || (Integer.parseInt(size) < MINIMUM_SIZE);
    }

    private boolean isNumeric(final String text) {
        if (text.isBlank()) {
            return false;
        }

        for (char character : text.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }

        return true;
    }
}
