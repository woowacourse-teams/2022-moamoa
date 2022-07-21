package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.study.controller.request.AttachedTagsRequest;
import com.woowacourse.moamoa.study.controller.request.StudyDetailsRequest;
import com.woowacourse.moamoa.study.controller.request.StudyPeriodRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatingStudyController {

    @PostMapping("/api/studies")
    public ResponseEntity<Void> createStudy(
            @Valid @RequestBody final StudyDetailsRequest studyDetailsRequest,
            @Valid @RequestBody final StudyPeriodRequest studyPeriodRequest,
            @Valid @RequestBody final AttachedTagsRequest attachedTagsRequest
    ) {
        return ResponseEntity.ok().build();
    }
}
