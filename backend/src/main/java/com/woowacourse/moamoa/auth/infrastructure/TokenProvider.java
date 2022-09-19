package com.woowacourse.moamoa.auth.infrastructure;

import com.woowacourse.moamoa.auth.service.response.TokensResponse;

public interface TokenProvider {

    TokensResponse createToken(final Long payload);

    String getPayload(final String token);

    String getPayloadWithExpiredToken(final String token);

    boolean isInvalidToken(final String token);

    TokensResponse recreationToken(final Long memberId, final String refreshToken);
}
