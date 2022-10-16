package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import com.woowacourse.moamoa.studyroom.service.response.temp.TempArticleResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studies/{study-id}/notice/draft-articles")
public class TempArticleController {

    private final TempArticleService tempArticleService;

    public TempArticleController(final TempArticleService tempArticleService) {
        this.tempArticleService = tempArticleService;
    }

    @PostMapping
    public ResponseEntity<CreatedTempArticleIdResponse> createTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final ArticleRequest request
    ) {
        final CreatedTempArticleIdResponse response = tempArticleService.createTempArticle(memberId, studyId, request);

        final String location = String.format(
                "/api/studies/%d/notice/draft-articles/%d", studyId, response.getDraftArticleId()
        );
        return ResponseEntity.created(URI.create(location)).body(response);
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<TempArticleResponse> getTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId
    ) {
        TempArticleResponse response = tempArticleService.getTempArticle(memberId, studyId, articleId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<Void> deleteTempArticle(
            @AuthenticatedMemberId final Long memberId,
            @PathVariable("study-id") final Long studyId,
            @PathVariable("article-id") final Long articleId
    ) {
        tempArticleService.deleteTempArticle(memberId, studyId, articleId);
        return ResponseEntity.noContent().build();
    }
}
