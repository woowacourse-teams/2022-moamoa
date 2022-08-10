package com.woowacourse.moamoa.study.service.request;

import java.time.LocalDate;
import java.util.List;

public class CreatingStudyRequestBuilder {

    private String title;

    private String excerpt;

    private String thumbnail;

    private String description;

    private Integer maxMemberCount;

    private LocalDate startDate;

    private LocalDate enrollmentEndDate;

    private LocalDate endDate;

    private List<Long> tagIds;

    public CreatingStudyRequestBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public CreatingStudyRequestBuilder excerpt(final String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public CreatingStudyRequestBuilder thumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public CreatingStudyRequestBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public CreatingStudyRequestBuilder maxMemberCount(final Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
        return this;
    }

    public CreatingStudyRequestBuilder startDate(final LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public CreatingStudyRequestBuilder enrollmentEndDate(final LocalDate enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
        return this;
    }

    public CreatingStudyRequestBuilder endDate(final LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public CreatingStudyRequestBuilder tagIds(final List<Long> tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public CreatingStudyRequest build() {
        return new CreatingStudyRequest(title, excerpt, thumbnail, description, maxMemberCount, startDate,
                enrollmentEndDate, endDate, tagIds);
    }
}
