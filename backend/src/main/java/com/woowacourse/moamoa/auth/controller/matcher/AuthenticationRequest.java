package com.woowacourse.moamoa.auth.controller.matcher;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;

public class AuthenticationRequest {

    private final HttpMethod method;
    private final String path;


    public AuthenticationRequest(final HttpMethod method, final String path) {
        this.method = method;
        this.path = path;
    }

    public boolean isMatch(HttpServletRequest request) {
        final Pattern pattern = Pattern.compile(path);

        return method.equals(HttpMethod.resolve(request.getMethod().toUpperCase()))
                && (path.equals(request.getServletPath()) || pattern.matcher(request.getServletPath()).matches());
    }
}
