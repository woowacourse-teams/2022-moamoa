package com.woowacourse.moamoa.auth.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginStatusResponse {

    final boolean isLoggedIn;
}
