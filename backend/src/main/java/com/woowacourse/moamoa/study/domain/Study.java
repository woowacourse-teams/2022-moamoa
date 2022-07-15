package com.woowacourse.moamoa.study.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;

    @OneToMany(mappedBy = "study")
    private List<StudyFilter> studyFilters = new ArrayList<>();

    public Study(final Long id, final String title, final String excerpt,
                 final String thumbnail, final String status
    ) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getStatus() {
        return status;
    }
}
