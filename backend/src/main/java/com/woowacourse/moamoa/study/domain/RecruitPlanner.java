package com.woowacourse.moamoa.study.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class RecruitPlanner {

    @Column(name = "max_member_count")
    private Integer max;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status")
    private RecruitStatus recruitStatus;

    private LocalDate enrollmentEndDate;

    protected RecruitPlanner() {
    }

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

    boolean isNeedToCloseRecruiting(final LocalDate now) {
        return recruitStatus.equals(RecruitStatus.RECRUITMENT_START) && isRecruitedBeforeThan(now);
    }

    void closeRecruiting() {
        recruitStatus = RecruitStatus.RECRUITMENT_END;
    }

    LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    boolean isCloseEnrollment() {
        return recruitStatus.equals(RecruitStatus.RECRUITMENT_END);
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

    public RecruitStatus getRecruitStatus() {
        return recruitStatus;
    }
}
