package com.woowacourse.moamoa.study.domain;

import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_END;
import static com.woowacourse.moamoa.study.domain.RecruitStatus.RECRUITMENT_START;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class RecruitPlanner {

    @Column(name = "max_member_count")
    private Integer max;

    @Enumerated(STRING)
    @Column(name = "recruitment_status")
    private RecruitStatus recruitStatus;

    private LocalDate enrollmentEndDate;

    public RecruitPlanner(final Integer max, final RecruitStatus recruitStatus, final LocalDate enrollmentEndDate) {
        this.max = max;
        this.recruitStatus = recruitStatus;
        this.enrollmentEndDate = enrollmentEndDate;
    }

    boolean hasEnrollmentEndDate() {
        return enrollmentEndDate != null;
    }

    boolean isRecruitedBeforeThan(LocalDate date) {
        if (!hasEnrollmentEndDate()) {
            return false;
        }
        return enrollmentEndDate.isBefore(date);
    }

    void updateRecruiting(final LocalDate now) {
        if (isNeedToCloseRecruiting(now)) {
            closeRecruiting();
        }
    }

    private boolean isNeedToCloseRecruiting(final LocalDate now) {
        return recruitStatus.equals(RECRUITMENT_START) && isRecruitedBeforeThan(now);
    }

    void startRecruiting() {
        recruitStatus = RECRUITMENT_START;
    }

    void closeRecruiting() {
        recruitStatus = RECRUITMENT_END;
    }

    public LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    boolean isCloseEnrollment() {
        return recruitStatus.equals(RECRUITMENT_END);
    }

    int getCapacity() {
        if (hasCapacity()) {
            return max;
        }
        throw new IllegalStateException("최대 인원 정보가 없습니다.");
    }

    boolean hasCapacity() {
        return max != null;
    }
}
