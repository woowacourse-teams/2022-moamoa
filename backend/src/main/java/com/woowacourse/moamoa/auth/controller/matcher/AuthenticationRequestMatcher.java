package com.woowacourse.moamoa.auth.controller.matcher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public class AuthenticationRequestMatcher {

    final List<AuthenticationRequest> authenticationRequests;

    public AuthenticationRequestMatcher(final Map<HttpMethod, List<String>> authenticationPaths) {
        this.authenticationRequests = authenticationPaths.keySet()
                .stream()
                .flatMap(method -> authenticationPaths.get(method)
                        .stream()
                        .map(path -> new AuthenticationRequest(method, path)))
                .collect(Collectors.toList());
    }

    public boolean isRequiredAuth(HttpServletRequest request) {
        return authenticationRequests.stream()
                .anyMatch(authenticationRequest -> authenticationRequest.isMatch(request));
    }
}
