package com.woowacourse.moamoa.community.controller;

import com.woowacourse.moamoa.auth.config.AuthenticationPrincipal;
import com.woowacourse.moamoa.community.domain.CommunityArticle;
import com.woowacourse.moamoa.community.service.CommunityArticleService;
import com.woowacourse.moamoa.community.service.request.ArticleRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityArticleController {

    private final CommunityArticleService communityArticleService;

    public CommunityArticleController(final CommunityArticleService communityArticleService) {
        this.communityArticleService = communityArticleService;
    }

    @PostMapping("/api/studies/{study-id}/community/articles")
    public ResponseEntity<Void> createArticle(@AuthenticationPrincipal final Long githubId,
                                              @PathVariable("study-id") final Long studyId,
                                              @Valid @RequestBody final ArticleRequest request
    ) {
        final CommunityArticle article = communityArticleService.createArticle(githubId, studyId, request);
        final URI location = URI.create("/api/studies/" + studyId + "/community/articles/" + article.getId());
        return ResponseEntity.created(location).build();
    }
}
