package com.woowacourse.moamoa.common.advice;

import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.common.advice.response.ErrorResponse;
import com.woowacourse.moamoa.common.exception.InvalidFormatException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler({
            InvalidFormatException.class,
            InvalidPeriodException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(final Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 형식입니다."));
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleUnauthorized(final UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("요청을 처리할 수 없습니다."));
    }
}
