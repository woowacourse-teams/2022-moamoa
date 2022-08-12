package com.woowacourse.moamoa.common.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.woowacourse.moamoa.auth.exception.RefreshTokenExpirationException;
import com.woowacourse.moamoa.common.advice.response.ErrorResponse;
import com.woowacourse.moamoa.common.exception.BadRequestException;
import com.woowacourse.moamoa.common.exception.InvalidFormatException;
import com.woowacourse.moamoa.common.exception.NotFoundException;
import com.woowacourse.moamoa.common.exception.UnauthorizedException;
import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import com.woowacourse.moamoa.study.service.exception.FailureParticipationException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 형식입니다."));
    }

    @ExceptionHandler({
            InvalidFormatException.class,
            InvalidPeriodException.class,
            BadRequestException.class,
            FailureParticipationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(final Exception e) {
        log.debug("HandleBadRequest : {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({UnauthorizedException.class, JwtException.class})
    public ResponseEntity<Void> handleUnauthorized(final Exception e) {
        log.debug("UnauthorizedException : {}", e.getMessage());
        return ResponseEntity.status(UNAUTHORIZED).build();
    }

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<ErrorResponse> handle(RefreshTokenExpirationException e) {
        log.debug("RefreshTokenExpirationException : {}", e.getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorResponse(e.getMessage(), 4001));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final Exception e) {
        log.debug("NotFoundException : {}", e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(RuntimeException e) {
        log.debug("RuntimeException : {}", e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse("요청을 처리할 수 없습니다."));
    }
}
