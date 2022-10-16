package com.woowacourse.moamoa.studyroom.controller;

import com.woowacourse.moamoa.auth.config.AuthenticatedMemberId;
import com.woowacourse.moamoa.studyroom.service.TempArticleService;
import com.woowacourse.moamoa.studyroom.service.request.ArticleRequest;
import com.woowacourse.moamoa.studyroom.service.response.temp.CreatedTempArticleIdResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            @AuthenticatedMemberId final Long authorId,
            @PathVariable("study-id") final Long studyId,
            @Valid @RequestBody final ArticleRequest request
    ) {
        final CreatedTempArticleIdResponse response = tempArticleService.createTempArticle(authorId, studyId, request);

        final String location = String.format(
                "/api/studies/%d/notice/draft-articles/%d", studyId, response.getDraftArticleId()
        );
        return ResponseEntity.created(URI.create(location)).body(response);
    }
}
