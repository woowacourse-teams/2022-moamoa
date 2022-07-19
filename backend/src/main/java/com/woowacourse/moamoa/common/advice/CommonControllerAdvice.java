package com.woowacourse.moamoa.common.advice;

import com.woowacourse.moamoa.auth.service.oauthclient.exception.UnauthorizedException;
import com.woowacourse.moamoa.common.advice.response.ErrorResponse;
import com.woowacourse.moamoa.common.exception.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequest(final InvalidFormatException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleUnauthorized(final UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
