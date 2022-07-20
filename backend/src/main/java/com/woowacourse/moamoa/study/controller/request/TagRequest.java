package com.woowacourse.moamoa.study.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TagRequest {

    private final List<Long> generation;
    private final List<Long> area;
    private final List<Long> tag;

    public TagRequest(final List<Long> generation, final List<Long> area, final List<Long> tag) {
        this.generation = Objects.requireNonNullElseGet(generation, ArrayList::new);
        this.area = Objects.requireNonNullElseGet(area, ArrayList::new);
        this.tag = Objects.requireNonNullElseGet(tag, ArrayList::new);
    }

    public List<Long> getFilterIds() {
        List<Long> filterIds = new ArrayList<>();
        filterIds.addAll(generation);
        filterIds.addAll(area);
        filterIds.addAll(tag);

        return filterIds;
    }

    public boolean isEmpty() {
        return getFilterIds().isEmpty();
    }
}
