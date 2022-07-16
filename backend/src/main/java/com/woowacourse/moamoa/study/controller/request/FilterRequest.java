package com.woowacourse.moamoa.study.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FilterRequest {

    private final List<String> generation;
    private final List<String> area;
    private final List<String> tag;

    public FilterRequest(final List<String> generation, final List<String> area, final List<String> tag) {
        this.generation = Objects.requireNonNullElseGet(generation, ArrayList::new);
        this.area = Objects.requireNonNullElseGet(area, ArrayList::new);
        this.tag = Objects.requireNonNullElseGet(tag, ArrayList::new);
    }

    public List<String> getFilterNames() {
        List<String> filterNames = new ArrayList<>();
        filterNames.addAll(generation);
        filterNames.addAll(area);
        filterNames.addAll(tag);

        return filterNames;
    }

    public boolean isEmpty() {
        return getFilterNames().isEmpty();
    }
}
