package com.woowacourse.moamoa.auth.config;

import static org.springframework.http.HttpHeaders.COOKIE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AuthenticationExtractor {

    private static final String ACCESS_TOKEN_TYPE = AuthenticationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(COOKIE);

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();

            final String accessToken = Arrays.stream(value.split(" "))
                    .filter(str -> str.startsWith("accessToken"))
                    .findAny().orElseThrow();

            final String[] splitAccessToken = accessToken.split("=");

            if (splitAccessToken.length < 2) {
                return null;
            }

            if (splitAccessToken[0].equals("accessToken")) {
                String authHeaderValue = splitAccessToken[1];
                request.setAttribute(ACCESS_TOKEN_TYPE, splitAccessToken[1]);
                int commaIndex = authHeaderValue.indexOf(',');

                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }

                return authHeaderValue;
            }
        }
        return null;
    }
}
