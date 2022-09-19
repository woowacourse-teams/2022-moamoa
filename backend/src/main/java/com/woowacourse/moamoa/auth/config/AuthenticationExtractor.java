package com.woowacourse.moamoa.auth.config;

import static org.springframework.http.HttpHeaders.COOKIE;
import static lombok.AccessLevel.PRIVATE;

import com.woowacourse.moamoa.auth.exception.TokenNotFoundException;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AuthenticationExtractor {

    private static final String ACCESS_TOKEN_TYPE = AuthenticationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final String COOKIE_DELIMITER = " ";
    private static final int COOKIE_LENGTH = 2;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String KEY_AND_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(COOKIE);

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();

            final String accessToken = Arrays.stream(value.split(COOKIE_DELIMITER))
                    .filter(str -> str.startsWith(ACCESS_TOKEN))
                    .findAny()
                    .orElseThrow(TokenNotFoundException::new);

            final String[] splitAccessToken = accessToken.split(KEY_AND_VALUE_DELIMITER);

            if (splitAccessToken.length < COOKIE_LENGTH) {
                return null;
            }

            final String cookieKey = splitAccessToken[KEY_INDEX];

            if (cookieKey.equals(ACCESS_TOKEN)) {
                String authHeaderValue = splitAccessToken[VALUE_INDEX];
                request.setAttribute(ACCESS_TOKEN_TYPE, splitAccessToken[VALUE_INDEX]);
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
