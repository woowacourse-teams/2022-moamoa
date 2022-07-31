package com.woowacourse.moamoa.study.query.data;

import static java.util.Objects.requireNonNull;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;

public class StudyDetailsDataBuilder {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String recruitStatus;
    private String description;
    private MemberData owner;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private LocalDate createdDate;
    private LocalDate enrollmentEndDate;
    private LocalDate startDate;
    private LocalDate endDate;

    StudyDetailsDataBuilder() { }

    public StudyDetailsDataBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudyDetailsDataBuilder title(String title) {
        this.title = title;
        return this;
    }

    public StudyDetailsDataBuilder excerpt(String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public StudyDetailsDataBuilder thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public StudyDetailsDataBuilder status(String recruitStatus) {
        this.recruitStatus = recruitStatus;
        return this;
    }

    public StudyDetailsDataBuilder description(String description) {
        this.description = description;
        return this;
    }

    public StudyDetailsDataBuilder owner(MemberData owner) {
        this.owner = owner;
        return this;
    }

    public StudyDetailsDataBuilder currentMemberCount(Integer currentMemberCount) {
        this.currentMemberCount = currentMemberCount;
        return this;
    }

    public StudyDetailsDataBuilder maxMemberCount(Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
        return this;
    }

    public StudyDetailsDataBuilder createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public StudyDetailsDataBuilder enrollmentEndDate(LocalDate enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
        return this;
    }

    public StudyDetailsDataBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public StudyDetailsDataBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public StudyDetailsData build() {
        return new StudyDetailsData(
                // Study Content
                requireNonNull(id), requireNonNull(title), requireNonNull(excerpt), requireNonNull(thumbnail),
                requireNonNull(recruitStatus), requireNonNull(description), requireNonNull(createdDate),
                // Study Participant
                requireNonNull(owner), requireNonNull(currentMemberCount), maxMemberCount,
                // Study Period
                enrollmentEndDate, requireNonNull(startDate), endDate);
    }
}
