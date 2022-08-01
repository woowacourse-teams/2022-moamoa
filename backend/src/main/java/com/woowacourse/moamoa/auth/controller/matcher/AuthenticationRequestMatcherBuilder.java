package com.woowacourse.moamoa.auth.controller.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequestMatcherBuilder {

    private static final List<HttpMethod> METHODS = List.of(
            HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.PATCH
    );

    private final Map<HttpMethod, List<String>> authenticationPaths;

    public AuthenticationRequestMatcherBuilder() {
        this.authenticationPaths = METHODS.stream()
                .collect(Collectors.toMap(method -> method, method -> new ArrayList<>()));
    }

    public AuthenticationRequestMatcherBuilder addUpAuthenticationPath(HttpMethod method, String... urls) {
        final List<String> paths = authenticationPaths.get(method);
        paths.addAll(List.of(urls));
        return this;
    }

    public AuthenticationRequestMatcher build() {
        return new AuthenticationRequestMatcher(authenticationPaths);
    }
}
