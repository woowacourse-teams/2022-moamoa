package com.woowacourse.moamoa.study.service.response;

import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
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
        this.title = study.getTitle();
        this.excerpt = study.getExcerpt();
        this.thumbnail = study.getThumbnail();
        this.status = study.getStatus();
        this.description = study.getDescription();
        this.currentMemberCount = study.getCurrentMemberCount();
        this.maxMemberCount = study.getMaxMemberCount();
        this.createdAt = study.getCreatedAt().toLocalDate().toString();
        this.enrollmentEndDate = getNullableDate(study.getEnrollmentEndDate());
        this.startDate = getNullableDate(study.getStartDate());
        this.endDate = getNullableDate(study.getEndDate());
        this.owner = owner;
        this.members = membersResponse;
        this.tags = attachedTags;
    }

    private String getNullableDate(final LocalDateTime study) {
        return Optional.ofNullable(study).map(date -> date.toLocalDate().toString()).orElse("");
    }
}
