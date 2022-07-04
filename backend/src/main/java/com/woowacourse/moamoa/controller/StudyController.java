package com.woowacourse.moamoa.controller;

import com.woowacourse.moamoa.controller.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class StudyController {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 정보입니다."));
    }

    @GetMapping("/api/studies")
    public ResponseEntity<ErrorResponse> getStudies(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 정보입니다."));
    }
}
