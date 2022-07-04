package com.woowacourse.moamoa.domain;

public class Study {

    private final Long id;
    private final String title;
    private final String description;
    private final String thumbnail;
    private final String status;

    public Study(Long id, String title, String description, String thumbnail, String status) {
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
