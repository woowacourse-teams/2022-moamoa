package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.studyroom.service.SearchingLinkArticleService;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
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
public class SearchingLinkArticleController {

    private final SearchingLinkArticleService searchingLinkArticleService;

    @GetMapping
    public ResponseEntity<LinksResponse> getLinks(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PageableDefault(size = 9) final Pageable pageable
    ) {
        final LinksResponse linksResponse = searchingLinkArticleService.getLinks(memberId, studyId, pageable);
        return ResponseEntity.ok().body(linksResponse);
    }
}
