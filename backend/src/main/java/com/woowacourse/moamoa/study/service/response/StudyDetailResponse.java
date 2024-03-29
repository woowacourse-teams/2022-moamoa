package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.member.query.data.OwnerData;
import com.woowacourse.moamoa.member.query.data.ParticipatingMemberData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.time.LocalDate;
import java.util.List;
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
    private String recruitmentStatus;
    private String description;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private LocalDate createdDate;
    private LocalDate enrollmentEndDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private OwnerData owner;
    private List<ParticipatingMemberData> members;
    private List<TagData> tags;

    public StudyDetailResponse(final StudyDetailsData study,
                               final List<ParticipatingMemberData> participants,
                               final List<TagData> attachedTags) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.excerpt = study.getExcerpt();
        this.thumbnail = study.getThumbnail();
        this.recruitmentStatus = study.getRecruitmentStatus();
        this.description = study.getDescription();
        this.currentMemberCount = study.getCurrentMemberCount();
        this.maxMemberCount = study.getMaxMemberCount();
        this.createdDate = study.getCreatedDate();
        this.enrollmentEndDate = study.getEnrollmentEndDate();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
        this.owner = study.getOwner();
        this.members = participants;
        this.tags = attachedTags;
    }
}
