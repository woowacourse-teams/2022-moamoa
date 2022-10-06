package com.woowacourse.moamoa.auth.controller.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.util.PathMatcher;

@AllArgsConstructor
@Getter
public class PathRequestMatcher {

    private final String path;
    private final HttpMethod method;

    public boolean match(final PathMatcher pathMatcher, final String targetPath, final String pathMethod) {
        return pathMatcher.match(path, targetPath) && this.method.matches(pathMethod);
    }
}
