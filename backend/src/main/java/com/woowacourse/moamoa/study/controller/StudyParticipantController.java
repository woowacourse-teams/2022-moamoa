package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.study.service.StudyParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/members")
@RequiredArgsConstructor
public class StudyParticipantController {

    private final StudyParticipantService studyParticipantService;

    @PostMapping
    public ResponseEntity<Void> participateStudy(
            @AuthenticatedMemberId final Long memberId, @PathVariable("study-id") final Long studyId
    ) {
        studyParticipantService.participateStudy(memberId, studyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> leaveStudy(
            @AuthenticatedMemberId final Long memberId, @PathVariable("study-id") final Long studyId
    ) {
        studyParticipantService.leaveStudy(memberId, studyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity<Void> kickOutMember(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("member-id") final Long participantId
    ) {

        return ResponseEntity.noContent().build();
    }
}
