package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.domain.Study;

public class StudyResponse {

    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private String status;

    public StudyResponse() {
    }

    public StudyResponse(final Study study) {
        this(study.getId(), study.getTitle(), study.getDescription(), study.getThumbnail(),
                study.getStatus());
    }

    public StudyResponse(
            final Long id, final String title, final String description,
            final String thumbnail, final String status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getStatus() {
        return status;
    }
}
