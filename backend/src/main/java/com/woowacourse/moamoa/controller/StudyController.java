package com.woowacourse.moamoa.controller;

import com.woowacourse.moamoa.controller.dto.ErrorResponse;
import com.woowacourse.moamoa.controller.dto.StudiesResponse;
import com.woowacourse.moamoa.exception.InvalidFormatException;
import com.woowacourse.moamoa.service.StudyService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse("잘못된 요청 정보입니다."));
    }

    @GetMapping("/api/studies")
    public ResponseEntity<StudiesResponse> getStudies(@PageableDefault(size = 5) Pageable pageable) {
        final StudiesResponse studiesResponse = studyService.getStudies(pageable);
        return ResponseEntity.ok().body(studiesResponse);
    }
}
