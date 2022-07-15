package com.woowacourse.moamoa.filter.controller;

import com.woowacourse.moamoa.filter.service.FilterService;
import com.woowacourse.moamoa.filter.service.response.FiltersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/api/tags")
    public ResponseEntity<FiltersResponse> getTags(
            @RequestParam(value = "tag-name", required = false, defaultValue = "") final String tagName
    ) {
        final FiltersResponse filtersResponse = filterService.getTags(tagName);

        return ResponseEntity.ok().body(filtersResponse);
    }
}
