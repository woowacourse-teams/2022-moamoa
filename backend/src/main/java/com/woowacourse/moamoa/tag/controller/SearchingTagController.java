package com.woowacourse.moamoa.tag.controller;

import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import com.woowacourse.moamoa.tag.service.SearchingTagService;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchingTagController {

    private final SearchingTagService searchingTagService;

    @GetMapping("/api/tags")
    public ResponseEntity<TagsResponse> searchTags(
            @RequestParam(value = "name", required = false, defaultValue = "") final String tagShortName,
            @RequestParam(value = "category", required = false, defaultValue = "") final CategoryIdRequest categoryIdRequest) {
        final TagsResponse tagsResponse = searchingTagService.getBy(tagShortName, categoryIdRequest);
        return ResponseEntity.ok().body(tagsResponse);
    }
}
