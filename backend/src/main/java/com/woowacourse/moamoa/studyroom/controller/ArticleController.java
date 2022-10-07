package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.service.ArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.ArticleResponse;
import com.woowacourse.moamoa.studyroom.service.response.ArticleSummariesResponse;
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
@RequestMapping("api/studies/{study-id}/{type}/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@AuthenticatedMemberId final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("type") final String typeName,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
        final Long articleId = articleService.createArticle(id, studyId, request.createContent(), type);
        final URI location = URI.create("/api/studies/" + studyId + "/" + typeName + "/articles/" + articleId);
        return ResponseEntity.created(location).header("Access-Control-Allow-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("article-id") final Long articleId,
                                                      @PathVariable("type") final String typeName
    ) {
        final ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
        ArticleResponse response = articleService.getArticle(articleId, type);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<Void> deleteArticle(@AuthenticatedMemberId final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @PathVariable("type") final String typeName
    ) {
        final ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
        articleService.deleteArticle(id, studyId, articleId, type);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticleSummariesResponse> getArticles(
            @PathVariable("study-id") final Long studyId,
            @PathVariable("type") final String typeName,
            @PageableDefault final Pageable pageable
    ) {
        final ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
        ArticleSummariesResponse response = articleService.getArticles(studyId, pageable, type);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<Void> updateArticle(@AuthenticatedMemberId final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @PathVariable("type") final String typeName,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final ArticleType type = ArticleType.valueOf(typeName.toUpperCase());
        articleService.updateArticle(id, studyId, articleId, request.createContent(), type);
        return ResponseEntity.noContent().build();
    }
}
