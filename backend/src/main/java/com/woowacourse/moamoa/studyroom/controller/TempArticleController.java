package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.CreatedArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.TempArticlesResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
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
public class TempArticleController {

    private final TempArticleService tempArticleService;

    public TempArticleController(final TempArticleService tempArticleService) {
        this.tempArticleService = tempArticleService;
    }

    @PostMapping("/api/studies/{study-id}/{article-type}/draft-articles")
    public ResponseEntity<CreatedTempArticleIdResponse> createTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final ArticleRequest request,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        final CreatedTempArticleIdResponse response = tempArticleService
                .createTempArticle(memberId, studyId, request, articleType);

        final String location = String.format(
                "/api/studies/%d/notice/draft-articles/%d", studyId, response.getDraftArticleId()
        );
        return ResponseEntity.created(URI.create(location)).body(response);
    }

    @GetMapping("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}")
    public ResponseEntity<TempArticleResponse> getTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        TempArticleResponse response = tempArticleService.getTempArticle(memberId, studyId, articleId, articleType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/draft/{article-type}/articles")
    public ResponseEntity<TempArticlesResponse> getTempArticles(
            @AuthenticatedMemberId final Long memberId,
            @PageableDefault final Pageable pageable,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        TempArticlesResponse response = tempArticleService.getTempArticles(memberId, pageable, articleType);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}")
    public ResponseEntity<Void> deleteTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        tempArticleService.deleteTempArticle(memberId, studyId, articleId, articleType);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}")
    public ResponseEntity<Void> updateTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId,
            @Valid @RequestBody final ArticleRequest request,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        tempArticleService.updateTempArticle(memberId, studyId, articleId, request, articleType);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/studies/{study-id}/{article-type}/draft-articles/{article-id}/publish")
    public ResponseEntity<CreatedArticleIdResponse> publishTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId,
            @PathVariable("article-type") final ArticleType articleType
    ) {
        CreatedArticleIdResponse response = tempArticleService.publishTempArticle(memberId, studyId, articleId, articleType);

        final String location = String.format(
                "/api/studies/%d/notice/articles/%d", studyId, response.getArticleId()
        );
        return ResponseEntity.created(URI.create(location)).body(response);
    }
}
