package com.woowacourse.moamoa.common.advice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.woowacourse.moamoa.common.advice.response.ErrorResponse;
import com.woowacourse.moamoa.common.exception.InvalidFormatException;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler({
            InvalidFormatException.class,
            InvalidPeriodException.class,
            FailureParticipationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(final Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleUnauthorized(final UnauthorizedException e) {
        return ResponseEntity.status(UNAUTHORIZED).build();
    }
}
