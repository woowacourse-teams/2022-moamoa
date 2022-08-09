package com.woowacourse.moamoa.auth.infrastructure;

import com.woowacourse.moamoa.auth.service.response.TokenResponseWithRefresh;

public interface TokenProvider {

    TokenResponseWithRefresh createToken(final Long payload);

    String getPayload(final String token);

    boolean validateToken(final String token);

    String recreationAccessToken(final String refreshToken);
}
