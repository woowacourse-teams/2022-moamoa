package com.woowacourse.moamoa.referenceroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.referenceroom.service.ReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.request.CreatingLinkRequest;
import com.woowacourse.moamoa.referenceroom.service.request.EditingLinkRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reference-room/links")
@RequiredArgsConstructor
public class ReferenceRoomController {

    private final ReferenceRoomService referenceRoomService;

    @PostMapping
    public ResponseEntity<Void> createLink(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final CreatingLinkRequest creatingLinkRequest
    ) {
        final Long id = referenceRoomService.createLink(githubId, studyId, creatingLinkRequest).getId();
        return ResponseEntity.created(URI.create("/api/studies/" + studyId + "/reference-room/links/" + id)).build();
    }

    @PutMapping("/{link-id}")
    public ResponseEntity<Void> updateLink(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long linkId,
            @Valid @RequestBody final EditingLinkRequest editingLinkRequest
    ) {
        referenceRoomService.updateLink(githubId, studyId, linkId, editingLinkRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{link-id}")
    public ResponseEntity<Void> deleteLink(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long linkId
    ) {
        referenceRoomService.deleteLink(githubId, studyId, linkId);
        return ResponseEntity.noContent().build();
    }
}