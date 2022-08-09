package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.community.domain.repository.CommunityArticleRepository;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityArticleController {

    public CommunityArticleController(final CommunityArticleRepository communityArticleRepository) {
        throw new UnsupportedOperationException(
                "CommunityArticleController#CommunityArticleController not implemented yet !!");
    }

    @PostMapping("/api/studies/{study-id}/community/articles")
    public ResponseEntity<Void> createArticle(
            @AuthenticationPrincipal final Long githubId,
            @PathVariable("study-id") final Long studyId,
            final ArticleRequest request) {
        return ResponseEntity.ok().build();
    }
}
