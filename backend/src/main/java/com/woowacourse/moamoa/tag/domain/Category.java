package com.woowacourse.moamoa.tag.domain;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Category {

    @Id
    private Long id;

    @Enumerated(value = STRING)
    private CategoryName name;

    @OneToMany
    @JoinColumn(name = "filter_id")
    private List<Tag> tags = new ArrayList<>();

    public Category(final Long id, final String name) {
        this.id = id;
        this.name = CategoryName.of(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public List<Tag> getTags() {
        return tags;
    }
}
