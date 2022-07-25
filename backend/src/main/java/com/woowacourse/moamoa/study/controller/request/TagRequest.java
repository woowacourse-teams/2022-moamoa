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
    private final List<Long> subject;

    public TagRequest(final List<Long> generation, final List<Long> area, final List<Long> subject) {
        this.generation = Objects.requireNonNullElseGet(generation, ArrayList::new);
        this.area = Objects.requireNonNullElseGet(area, ArrayList::new);
        this.subject = Objects.requireNonNullElseGet(subject, ArrayList::new);
    }

    public List<Long> getTagIds() {
        List<Long> tagIds = new ArrayList<>();
        tagIds.addAll(generation);
        tagIds.addAll(area);
        tagIds.addAll(subject);

        return tagIds;
    }

    public boolean isEmpty() {
        return getTagIds().isEmpty();
    }
}
