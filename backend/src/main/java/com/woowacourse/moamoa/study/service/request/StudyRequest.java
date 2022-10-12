package com.woowacourse.moamoa.study.service.request;

import static com.woowacourse.moamoa.study.domain.StudyStatus.IN_PROGRESS;
import static com.woowacourse.moamoa.study.domain.StudyStatus.PREPARE;

import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Content;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.RecruitPlanner;
import com.woowacourse.moamoa.study.domain.RecruitStatus;
import com.woowacourse.moamoa.study.domain.StudyPlanner;
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
public class StudyRequest {

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

    @NotNull
    private List<Long> tagIds;

    public List<Long> getTagIds() {
        return tagIds == null ? List.of() : tagIds;
    }

    public StudyPlanner mapToStudyPlanner(final LocalDate now) {
        if (startDate.equals(now)) {
            return new StudyPlanner(startDate, endDate, IN_PROGRESS);
        }
        return new StudyPlanner(startDate, endDate, PREPARE);
    }

    public Content mapToContent() {
        return new Content(title, excerpt, thumbnail, description);
    }

    public Participants mapToParticipants(Long ownerId) {
        return Participants.createBy(ownerId);
    }

    public RecruitPlanner mapToRecruitPlan() {
        return new RecruitPlanner(maxMemberCount, RecruitStatus.RECRUITMENT_START, enrollmentEndDate);
    }

    public AttachedTags mapToAttachedTags() {
        final List<AttachedTag> attachedTags = getTagIds().stream()
                .map(AttachedTag::new)
                .collect(Collectors.toList());
        return new AttachedTags(attachedTags);
    }
}
