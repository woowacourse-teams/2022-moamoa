package com.woowacourse.moamoa.auth.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponse {

    private final String accessToken;
}
