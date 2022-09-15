package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.studyroom.domain.Article;
import com.woowacourse.moamoa.studyroom.domain.ArticleType;
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
@RequestMapping("api/studies/{study-id}/{article-type}/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-type") final ArticleType type,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final Article article = articleService.createArticle(id, studyId, request, type);
        final URI location = URI.create("/api/studies/" + studyId + "/" + type.lowerName() + "/articles/" + article.getId());
        return ResponseEntity.created(location).header("Access-Control-Allow-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleResponse> getArticle(@AuthenticatedMember final Long id,
                                                      @PathVariable("study-id") final Long studyId,
                                                      @PathVariable("article-type") final ArticleType articleType,
                                                      @PathVariable("article-id") final Long articleId
    ) {
        ArticleResponse response = articleService.getArticle(id, studyId, articleId, articleType);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{article-id}")
    public ResponseEntity<Void> deleteArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @PathVariable("article-type") final ArticleType type
    ) {
        articleService.deleteArticle(id, studyId, articleId, type);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticleSummariesResponse> getArticles(@AuthenticatedMember final Long id,
                                                                @PathVariable("study-id") final Long studyId,
                                                                @PathVariable("article-type") final ArticleType type,
                                                                @PageableDefault final Pageable pageable
    ) {
        ArticleSummariesResponse response = articleService.getArticles(id, studyId, pageable, type);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<Void> updateArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @PathVariable("article-type") final ArticleType type,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        articleService.updateArticle(id, studyId, articleId, request, type);
        return ResponseEntity.noContent().build();
    }
}
