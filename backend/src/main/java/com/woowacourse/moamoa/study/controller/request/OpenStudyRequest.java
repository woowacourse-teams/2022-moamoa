package com.woowacourse.moamoa.study.controller.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
public class OpenStudyRequest {

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

    private List<Long> tagIds = List.of();

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

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(startDate, LocalTime.of(0, 0));
    }

    public LocalDateTime getEnrollmentEndDateTime() {
        if (enrollmentEndDate == null) {
            return null;
        }
        return LocalDateTime.of(enrollmentEndDate, LocalTime.of(0, 0));
    }

    public LocalDateTime getEndDateTime() {
        if (endDate == null) {
            return null;
        }
        return LocalDateTime.of(endDate, LocalTime.of(0, 0));
    }
}
