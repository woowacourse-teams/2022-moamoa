package com.woowacourse.moamoa.auth.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseWithRefresh {

    private final String accessToken;
    private final String refreshToken;
}
