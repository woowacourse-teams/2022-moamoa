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
<<<<<<< HEAD
    private final List<Long> subject;

    public TagRequest(final List<Long> generation, final List<Long> area, final List<Long> subject) {
=======
    private final List<Long> tag;

    public TagRequest(final List<Long> generation, final List<Long> area, final List<Long> tag) {
>>>>>>> 8b63d80289f2b8b1703255683f3785c6c83d810f
        this.generation = Objects.requireNonNullElseGet(generation, ArrayList::new);
        this.area = Objects.requireNonNullElseGet(area, ArrayList::new);
        this.subject = Objects.requireNonNullElseGet(subject, ArrayList::new);
    }

<<<<<<< HEAD
    public List<Long> getTagIds() {
        List<Long> tagIds = new ArrayList<>();
        tagIds.addAll(generation);
        tagIds.addAll(area);
        tagIds.addAll(subject);

        return tagIds;
    }

    public boolean isEmpty() {
        return getTagIds().isEmpty();
=======
    public List<Long> getFilterIds() {
        List<Long> filterIds = new ArrayList<>();
        filterIds.addAll(generation);
        filterIds.addAll(area);
        filterIds.addAll(tag);

        return filterIds;
    }

    public boolean isEmpty() {
        return getFilterIds().isEmpty();
>>>>>>> 8b63d80289f2b8b1703255683f3785c6c83d810f
    }
}
