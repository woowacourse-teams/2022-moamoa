package com.woowacourse.moamoa.auth.controller.interceptor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Component
public class PathMatcherContainer {

    private final PathMatcher pathMatcher;
    private final List<PathRequestMatcher> includePathPatterns;
    private final List<PathRequestMatcher> excludePathPatterns;

    public PathMatcherContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPatterns = new ArrayList<>();
        this.excludePathPatterns = new ArrayList<>();
    }

    public boolean isNotIncludePath(final String targetPath, final String pathMethod) {
        final boolean excludePattern = excludePathPatterns.stream()
                .anyMatch(requestPath -> requestPath.match(pathMatcher, targetPath, pathMethod));

        final boolean isNotIncludePattern = includePathPatterns.stream()
                .noneMatch(requestPath -> requestPath.match(pathMatcher, targetPath, pathMethod));

        return excludePattern || isNotIncludePattern;
    }

    public void includePathPattern(final String path, final HttpMethod method) {
        this.includePathPatterns.add(new PathRequestMatcher(path, method));
    }

    public void excludePathPattern(final String path, final HttpMethod method) {
        this.excludePathPatterns.add(new PathRequestMatcher(path, method));
    }
}
