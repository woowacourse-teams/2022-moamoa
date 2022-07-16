package com.woowacourse.moamoa.filter.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Category {

    @Id
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "filter_id")
    private List<Filter> filters = new ArrayList<>();

    public Category(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
