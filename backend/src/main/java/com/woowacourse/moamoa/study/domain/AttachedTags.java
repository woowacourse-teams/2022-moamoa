package com.woowacourse.moamoa.study.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import lombok.Getter;

@Getter
@Embeddable
public class AttachedTags {

    @ElementCollection
    @CollectionTable(name = "study_tag", joinColumns = @JoinColumn(name = "study_id"))
    private List<AttachedTag> attachedTags = new ArrayList<>();

    protected AttachedTags() { }

    public AttachedTags(final List<AttachedTag> attachedTags) {
        this.attachedTags = attachedTags;
    }

    public List<AttachedTag> getValue() {
        return new ArrayList<>(attachedTags);
    }

    public static AttachedTags empty() {
        return new AttachedTags(List.of());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttachedTags that = (AttachedTags) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachedTags);
    }
}
