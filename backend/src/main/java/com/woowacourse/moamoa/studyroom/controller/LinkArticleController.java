package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.request.LinkArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.LinkArticlesResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkArticleController {

    private final ArticleService articleService;

    public LinkArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/api/studies/{study-id}/reference-room/links")
    public ResponseEntity<Void> createLink(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final LinkArticleRequest linkArticleRequest
    ) {
        final Long id = articleService.createArticle(memberId, studyId, linkArticleRequest, ArticleType.LINK).getId();
        return ResponseEntity.created(URI.create("/api/studies/" + studyId + "/reference-room/links/" + id)).build();
    }

    @GetMapping("/api/studies/{study-id}/reference-room/links")
    public ResponseEntity<LinkArticlesResponse> getLinkArticles(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PageableDefault(size = 9) final Pageable pageable
    ) {
        final LinkArticlesResponse response = articleService
                .getLinkArticles(memberId, studyId, pageable);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/studies/{study-id}/reference-room/links/{link-id}")
    public ResponseEntity<Void> deleteLink(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long articleId
    ) {
        articleService.deleteArticle(memberId, studyId, articleId, ArticleType.LINK);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/studies/{study-id}/reference-room/links/{link-id}")
    public ResponseEntity<Void> updateLink(
            @AuthenticatedMember final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("link-id") final Long articleId,
            @Valid @RequestBody final LinkArticleRequest linkArticleRequest
    ) {
        articleService.updateArticle(memberId, studyId, articleId, linkArticleRequest, ArticleType.LINK);
        return ResponseEntity.noContent().build();
    }

}
