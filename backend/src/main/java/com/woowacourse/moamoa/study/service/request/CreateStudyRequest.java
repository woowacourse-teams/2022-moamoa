package com.woowacourse.moamoa.study.service.request;

import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateStudyRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String excerpt;

    @NotBlank
    private String thumbnail;

    @NotBlank
    private String description;

    @Min(1)
    private Integer maxMemberCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private List<Long> tagIds;

    public List<Long> getTagIds() {
        return tagIds == null ? List.of() : tagIds;
    }

    public String getEndDate() {
        return endDate == null ? "" : endDate.toString();
    }

    public String getMaxMemberCount() {
        return maxMemberCount == null ? "" : String.valueOf(maxMemberCount);
    }

    public String getEnrollmentEndDate() {
        return enrollmentEndDate == null ? "" : enrollmentEndDate.toString();
    }

    public Period mapToPeriod() {
        return new Period(enrollmentEndDate, startDate, endDate);
    }

    public Details mapToDetails() {
        return new Details(title, excerpt, thumbnail, "OPEN", description);
    }

    public Participants mapToParticipants(Long ownerId) {
        return Participants.createByMaxSize(maxMemberCount, ownerId);
    }

    public AttachedTags mapToAttachedTags() {
        final List<AttachedTag> attachedTags = getTagIds().stream()
                .map(AttachedTag::new)
                .collect(Collectors.toList());
        return new AttachedTags(attachedTags);
    }
}
