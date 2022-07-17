package com.woowacourse.moamoa.study.domain.study;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.study.domain.studyfilter.StudyFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Study study = (Study) o;
        return Objects.equals(id, study.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
