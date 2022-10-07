package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.studyroom.service.LinkArticleService;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.LinksResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/reference-room/links")
public class LinkArticleController {

    private final LinkArticleService linkArticleService;

    public LinkArticleController(final LinkArticleService linkArticleService) {
        this.linkArticleService = linkArticleService;
    }

    @GetMapping
    public ResponseEntity<LinksResponse> getLinks(
            @PathVariable("study-id") final Long studyId,
            @PageableDefault(size = 9) final Pageable pageable
    ) {
        final LinksResponse linksResponse = linkArticleService.getLinks(studyId, pageable);
        return ResponseEntity.ok().body(linksResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createLink(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final LinkArticleRequest articleRequest
    ) {
        final Long id = linkArticleService.createArticle(memberId, studyId, articleRequest.createContent());
        return ResponseEntity.created(URI.create("/api/studies/" + studyId + "/reference-room/links/" + id)).build();
    }

    @PutMapping("/{link-id}")
    public ResponseEntity<Void> updateLink(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long linkId,
            @Valid @RequestBody final LinkArticleRequest articleRequest
    ) {
        linkArticleService.updateArticle(memberId, studyId, linkId, articleRequest.createContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{link-id}")
    public ResponseEntity<Void> deleteLink(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long linkId
    ) {
        linkArticleService.deleteArticle(memberId, studyId, linkId);
        return ResponseEntity.noContent().build();
    }
}
