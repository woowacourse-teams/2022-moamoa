package com.woowacourse.moamoa.tag.controller;

import com.woowacourse.moamoa.tag.domain.CategoryId;
import com.woowacourse.moamoa.tag.query.TagsSearcher;
import com.woowacourse.moamoa.tag.query.response.TagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchingTagController {

    private final TagsSearcher tagsSearcher;

    @GetMapping("/api/tags")
    public ResponseEntity<TagsResponse> searchTags(
            @RequestParam(required = false, defaultValue = "") final String tagShortName,
            @RequestParam(value = "category", required = false, defaultValue = "") final CategoryId categoryId) {
        final String trimmedTagShortName = tagShortName.trim();
        final TagsResponse tagsResponse = tagsSearcher.searchBy(trimmedTagShortName, categoryId);
        return ResponseEntity.ok().body(tagsResponse);
    }
}
