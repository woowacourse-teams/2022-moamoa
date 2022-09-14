package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMember;
import com.woowacourse.moamoa.studyroom.domain.article.Article;
import com.woowacourse.moamoa.studyroom.service.NoticeArticleService;
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
@RequestMapping("api/studies/{study-id}/notice/articles")
public class NoticeArticleController {

    private final NoticeArticleService noticeArticleService;

    public NoticeArticleController(final NoticeArticleService noticeArticleService) {
        this.noticeArticleService = noticeArticleService;
    }

    @PostMapping
    public ResponseEntity<Void> createArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final Article article = noticeArticleService.createArticle(id, studyId, request);
        final URI location = URI.create("/api/studies/" + studyId + "/notice/articles/" + article.getId());
        return ResponseEntity.created(location).header("Access-Control-Allow-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleResponse> getArticle(@AuthenticatedMember final Long id,
                                                      @PathVariable("study-id") final Long studyId,
                                                      @PathVariable("article-id") final Long articleId
    ) {
        ArticleResponse response = noticeArticleService.getArticle(id, studyId, articleId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{article-id}")
    public ResponseEntity<Void> deleteArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId
    ) {
        noticeArticleService.deleteArticle(id, studyId, articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticleSummariesResponse> getArticles(@AuthenticatedMember final Long id,
                                                                @PathVariable("study-id") final Long studyId,
                                                                @PageableDefault final Pageable pageable
    ) {
        ArticleSummariesResponse response = noticeArticleService.getArticles(id, studyId, pageable);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<Void> updateArticle(@AuthenticatedMember final Long id,
                                              @PathVariable("study-id") final Long studyId,
                                              @PathVariable("article-id") final Long articleId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        noticeArticleService.updateArticle(id, studyId, articleId, request);
        return ResponseEntity.noContent().build();
    }
}
