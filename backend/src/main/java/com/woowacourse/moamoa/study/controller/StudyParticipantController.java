package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/participants")
public class StudyParticipantController {

    @DeleteMapping
    public ResponseEntity<Void> leaveStudy(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId
    ) {
        return ResponseEntity.ok().build();
    }
}
