package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
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
    private Integer maxMemberCount;
    private String createdAt;
    private String enrollmentEndDate;
    private String startDate;
    private String endDate;
    private MemberResponse owner;
    private List<MemberResponse> members;
    private List<TagResponse> tags;

    public StudyDetailResponse(final Study study, final MemberResponse owner, final List<MemberResponse> membersResponse,
                               final List<TagResponse> attachedTags) {
        this.id = study.getId();
        this.title = study.getDetails().getTitle();
        this.excerpt = study.getDetails().getExcerpt();
        this.thumbnail = study.getDetails().getThumbnail();
        this.status = study.getDetails().getStatus();
        this.description = study.getDetails().getDescription();
        this.currentMemberCount = study.getParticipants().getSize();
        this.maxMemberCount = study.getParticipants().getMax();
        this.createdAt = getNullableDate(study.getCreatedAt());
        this.enrollmentEndDate = getNullableDate(study.getPeriod().getEnrollmentEndDate());
        this.startDate = getNullableDate(study.getPeriod().getStartDate());
        this.endDate = getNullableDate(study.getPeriod().getEndDate());
        this.owner = owner;
        this.members = membersResponse;
        this.tags = attachedTags;
    }

    private String getNullableDate(final LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map(date -> date.toLocalDate().toString()).orElse("");
    }
}
