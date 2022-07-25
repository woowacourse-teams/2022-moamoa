package com.woowacourse.moamoa.auth.infrastructure;

public interface TokenProvider {

    String createToken(final Long payload);

    String getPayload(final String token);

    boolean validateToken(final String token);
}
