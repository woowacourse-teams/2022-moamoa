package com.woowacourse.moamoa.study.service.request;

import java.time.LocalDate;
import java.util.List;

public class StudyRequestBuilder {

    private String title;

    private String excerpt;

    private String thumbnail;

    private String description;

    private Integer maxMemberCount;

    private LocalDate startDate;

    private LocalDate enrollmentEndDate;

    private LocalDate endDate;

    private List<Long> tagIds;

    public StudyRequestBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public StudyRequestBuilder excerpt(final String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public StudyRequestBuilder thumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public StudyRequestBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public StudyRequestBuilder maxMemberCount(final Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
        return this;
    }

    public StudyRequestBuilder startDate(final LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public StudyRequestBuilder enrollmentEndDate(final LocalDate enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
        return this;
    }

    public StudyRequestBuilder endDate(final LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public StudyRequestBuilder tagIds(final List<Long> tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public StudyRequest build() {
        return new StudyRequest(title, excerpt, thumbnail, description, maxMemberCount, startDate,
                enrollmentEndDate, endDate, tagIds);
    }
}
