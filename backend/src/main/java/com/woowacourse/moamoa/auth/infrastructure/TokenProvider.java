package com.woowacourse.moamoa.auth.infrastructure;

import com.woowacourse.moamoa.auth.service.response.TokenResponse;

public interface TokenProvider {

    TokenResponse createToken(final Long payload);

    String getPayload(final String token);

    boolean validateToken(final String token);
}
