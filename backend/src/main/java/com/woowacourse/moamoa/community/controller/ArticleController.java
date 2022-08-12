package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.community.domain.Article;
import com.woowacourse.moamoa.community.service.ArticleService;
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
@RequestMapping("api/studies/{study-id}/{article-type}/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-type") final String articleType,
                                              @Valid @RequestBody final ArticleRequest request) {
        final Article article = articleService.createArticle(id, studyId, request, articleType);
        final URI location = URI.create("/api/studies/" + studyId + "/" + articleType + "/articles/" + article.getId());
        return ResponseEntity.created(location).header("Access-Control-Allow-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleResponse> getArticle(@AuthenticatedMember final Long id,
                                                      @PathVariable("study-id") final Long studyId,
                                                      @PathVariable("article-type") final String articleType, 
                                                      @PathVariable("article-id") final Long articleId
    ) {
        ArticleResponse response = articleService.getArticle(id, studyId, articleId, articleType);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{article-id}")
    public ResponseEntity<Void> deleteArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-type") final String articleType, 
                                              @PathVariable("article-id") final Long articleId
    ) {
        articleService.deleteArticle(id, studyId, articleId, articleType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticleSummariesResponse> getArticles(@AuthenticatedMember final Long id,
                                                                @PathVariable("study-id") final Long studyId,
                                                                @PathVariable("article-type") final String articleType, 
                                                                @PageableDefault final Pageable pageable
    ) {
        ArticleSummariesResponse response = articleService.getArticles(id, studyId, pageable, articleType);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<Void> updateArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-type") final String articleType, 
                                              @PathVariable("article-id") final Long articleId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        articleService.updateArticle(id, studyId, articleId, request, articleType);
        return ResponseEntity.noContent().build();
    }
}
