package com.woowacourse.moamoa.studyroom.linksharingroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.studyroom.linksharingroom.service.request.CreatingLinkRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/link-sharing-rooms")
public class LinkSharingRoomController {

    @PostMapping
    public ResponseEntity<Void> createLink(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final CreatingLinkRequest creatingLinkRequest
    ) {
        return ResponseEntity.ok().build();
    }
}
