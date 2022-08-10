package com.woowacourse.moamoa.referenceroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.referenceroom.service.SearchingReferenceRoomService;
import com.woowacourse.moamoa.referenceroom.service.response.LinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reference-room/links")
@RequiredArgsConstructor
public class SearchingReferenceRoomController {

    private final SearchingReferenceRoomService searchingReferenceRoomService;

    @GetMapping
    public ResponseEntity<LinksResponse> getLinks(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            @PageableDefault(size = 5) final Pageable pageable
    ) {
        final LinksResponse linksResponse = searchingReferenceRoomService.getLinks(githubId, studyId, pageable);
        return ResponseEntity.ok().body(linksResponse);
    }
}
