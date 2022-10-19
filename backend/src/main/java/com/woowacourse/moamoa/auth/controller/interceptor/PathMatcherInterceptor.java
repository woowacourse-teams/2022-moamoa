package com.woowacourse.moamoa.auth.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class PathMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final PathMatcherContainer pathMatcherContainer;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        if (pathMatcherContainer.isNotIncludePath(request.getRequestURI(), request.getMethod())) {
            return true;
        }

        return this.handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor includePathPattern(final String pathPattern) {
        pathMatcherContainer.includePathPattern(pathPattern, HttpMethod.POST);
        pathMatcherContainer.includePathPattern(pathPattern, HttpMethod.DELETE);
        pathMatcherContainer.includePathPattern(pathPattern, HttpMethod.GET);
        pathMatcherContainer.includePathPattern(pathPattern, HttpMethod.PUT);
        pathMatcherContainer.includePathPattern(pathPattern, HttpMethod.PATCH);
        return this;
    }

    public PathMatcherInterceptor includePathPattern(final String pathPattern, final HttpMethod method) {
        this.pathMatcherContainer.includePathPattern(pathPattern, method);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(final String pathPattern, final HttpMethod method) {
        this.pathMatcherContainer.excludePathPattern(pathPattern, method);
        return this;
    }
}
