package com.woowacourse.moamoa.filter.controller;

import com.woowacourse.moamoa.filter.domain.CategoryId;
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

    @GetMapping("/api/filters")
    public ResponseEntity<FiltersResponse> getFilters(
            @RequestParam(required = false, defaultValue = "") final String name,
            @RequestParam(value = "category", required = false, defaultValue = "") final CategoryId categoryId) {
        final FiltersResponse filtersResponse = filterService.getFilters(name, categoryId);
        return ResponseEntity.ok().body(filtersResponse);
    }
}
