package com.woowacourse.moamoa.controller;

import com.woowacourse.moamoa.controller.dto.ErrorResponse;
import com.woowacourse.moamoa.controller.dto.StudiesResponse;
import com.woowacourse.moamoa.service.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 정보입니다."));
    }

    @GetMapping("/api/studies")
    public ResponseEntity<?> getStudies(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "5") int size) {

        if (!isValid(page, size)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 정보입니다."));
        }

        final StudiesResponse studiesResponse = studyService.getStudies(page, size);
        return ResponseEntity.ok().body(studiesResponse);
    }

    private boolean isValid(int page, int size) {
        return page >= 1 && size >= 1;
    }
}
