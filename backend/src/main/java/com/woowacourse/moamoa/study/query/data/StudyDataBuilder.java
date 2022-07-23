package com.woowacourse.moamoa.study.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDateTime;

public class StudyDataBuilder {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;
    private String description;
    private MemberData owner;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private LocalDateTime createdAt;
    private LocalDateTime enrollmentEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public StudyDataBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudyDataBuilder title(String title) {
        this.title = title;
        return this;
    }

    public StudyDataBuilder excerpt(String excerpt) {
        this.excerpt = excerpt;
        return this;
    }
    public StudyDataBuilder thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
    public StudyDataBuilder status(String status) {
        this.status = status;
        return this;
    }
    public StudyDataBuilder description(String description) {
        this.description = description;
        return this;
    }
    public StudyDataBuilder owner(MemberData owner) {
        this.owner = owner;
        return this;
    }
    public StudyDataBuilder currentMemberCount(int currentMemberCount) {
        this.currentMemberCount = currentMemberCount;
        return this;
    }
    public StudyDataBuilder maxMemberCount(int maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
        return this;
    }
    public StudyDataBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public StudyDataBuilder enrollmentEndDate(LocalDateTime enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
        return this;
    }
    public StudyDataBuilder startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }
    public StudyDataBuilder endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public StudyData build() {
        return new StudyData(id, title, excerpt, thumbnail, status, description, owner,
                currentMemberCount, maxMemberCount, createdAt, enrollmentEndDate, startDate, endDate);
    }
}
