package com.woowacourse.moamoa.tag.controller;

import com.woowacourse.moamoa.tag.service.TagService;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/tags")
    public ResponseEntity<TagsResponse> getTags() {
        TagsResponse tagsResponse = tagService.getTags();

        return ResponseEntity.ok().body(tagsResponse);
    }
}
