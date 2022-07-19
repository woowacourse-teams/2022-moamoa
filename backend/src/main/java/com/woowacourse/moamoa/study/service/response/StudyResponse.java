package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.study.domain.study.Study;

public class StudyResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;

    public StudyResponse() {
    }

    public StudyResponse(final Study study) {
        this(study.getId(), study.getTitle(), study.getExcerpt(), study.getThumbnail(),
                study.getStatus());
    }

    public StudyResponse(
            final Long id, final String title, final String excerpt,
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
