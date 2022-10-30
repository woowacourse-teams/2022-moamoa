package com.woowacourse.moamoa.study.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.study.service.AsyncService;
import com.woowacourse.moamoa.study.service.SynchronizedParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies/{study-id}/members")
public class StudyParticipantController {

    private final SynchronizedParticipantService synchronizedParticipantService;
    private final AsyncService asyncService;

    @PostMapping
    public ResponseEntity<Void> participateStudy(
            @AuthenticatedMemberId final Long memberId, @PathVariable("study-id") final Long studyId
    ) {
        synchronizedParticipantService.participateStudy(memberId, studyId);
        asyncService.send(studyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> leaveStudy(
            @AuthenticatedMemberId final Long memberId, @PathVariable("study-id") final Long studyId
    ) {
        synchronizedParticipantService.leaveStudy(memberId, studyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity<Void> kickOutMember(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("member-id") final Long participantId
    ) {
        synchronizedParticipantService.kickOutMember(memberId, studyId, participantId);
        return ResponseEntity.noContent().build();
    }
}
