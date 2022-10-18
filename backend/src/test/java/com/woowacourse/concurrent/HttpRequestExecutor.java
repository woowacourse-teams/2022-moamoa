package com.woowacourse.concurrent;

import org.springframework.http.HttpStatus;

@FunctionalInterface
interface HttpRequestExecutor {

    HttpStatus execute();
}
