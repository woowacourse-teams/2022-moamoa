package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.service.CommunityArticleService;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import com.woowacourse.moamoa.community.service.response.ArticleResponse;
import com.woowacourse.moamoa.community.service.response.ArticleSummariesResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("api/studies/{study-id}/community/articles")
public class CommunityArticleController {

    private final CommunityArticleService communityArticleService;

    public CommunityArticleController(final CommunityArticleService communityArticleService) {
        this.communityArticleService = communityArticleService;
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final CommunityArticle article = communityArticleService.createArticle(id, studyId, request);
        final URI location = URI.create("/api/studies/" + studyId + "/community/articles/" + article.getId());
        return ResponseEntity.created(location).header("Access-Control-Allow-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleResponse> getArticle(@AuthenticatedMember final Long id,
                                                      @PathVariable("study-id") final Long studyId,
                                                      @PathVariable("article-id") final Long articleId
    ) {
        ArticleResponse response = communityArticleService.getArticle(id, studyId, articleId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{article-id}")
    public ResponseEntity<Void> deleteArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId
    ) {
        communityArticleService.deleteArticle(id, studyId, articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticleSummariesResponse> getArticles(@AuthenticatedMember final Long id,
                                                                @PathVariable("study-id") final Long studyId,
                                                                @PageableDefault final Pageable pageable
    ) {
        ArticleSummariesResponse response = communityArticleService.getArticles(id, studyId, pageable);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<Void> updateArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        communityArticleService.updateArticle(id, studyId, articleId, request);
        return ResponseEntity.noContent().build();
    }
}
