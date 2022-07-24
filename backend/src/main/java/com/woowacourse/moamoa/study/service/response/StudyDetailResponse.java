package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
public class StudyDetailResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;
    private String description;
    private Integer currentMemberCount;
    private String maxMemberCount;
    private String createdAt;
    private String enrollmentEndDate;
    private String startDate;
    private String endDate;
    private MemberData owner;
    private List<MemberData> members;
    private List<TagData> tags;

    public StudyDetailResponse(final StudyDetailsData study,
                               final List<MemberData> participants,
                               final List<TagData> attachedTags) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.excerpt = study.getExcerpt();
        this.thumbnail = study.getThumbnail();
        this.status = study.getStatus();
        this.description = study.getDescription();
        this.currentMemberCount = study.getCurrentMemberCount();
        this.maxMemberCount = getNullableDate(study.getMaxMemberCount());
        this.createdAt = study.getCreatedAt().toString();
        this.enrollmentEndDate = getNullableDate(study.getEnrollmentEndDate());
        this.startDate = study.getStartDate().toString();
        this.endDate = getNullableDate(study.getEndDate());
        this.owner = study.getOwner();
        this.members = participants;
        this.tags = attachedTags;
    }

    private String getNullableDate(final Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
