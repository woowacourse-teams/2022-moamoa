package com.woowacourse.moamoa.controller.dto;

import com.woowacourse.moamoa.domain.Study;

public class StudyResponse {

    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private String status;

    public StudyResponse() {
    }

    public StudyResponse(Study study) {
        this(study.getId(), study.getTitle(), study.getDescription(), study.getThumbnail(), study.getStatus());
    }

    public StudyResponse(Long id, String title, String description, String thumbnail, String status) {
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
