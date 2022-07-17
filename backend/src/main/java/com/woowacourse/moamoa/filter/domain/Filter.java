package com.woowacourse.moamoa.filter.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.studyfilter.StudyFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Filter {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "filter")
    private List<StudyFilter> studyFilters = new ArrayList<>();

    public Filter(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Filter(final Long id, final String name, final Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
