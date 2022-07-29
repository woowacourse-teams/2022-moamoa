package com.woowacourse.moamoa.study.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class RecruitPlan {

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_status")
    private RecruitStatus recruitStatus;

    private LocalDate enrollmentEndDate;

    protected RecruitPlan() {}

    public RecruitPlan(final RecruitStatus recruitStatus, final LocalDate enrollmentEndDate) {
        this.recruitStatus = recruitStatus;
        this.enrollmentEndDate = enrollmentEndDate;
    }

    boolean hasEnrollmentEndDate() {
        return getEnrollmentEndDate() != null;
    }

    boolean isRecruitingBeforeThan(LocalDate date) {
        if (!hasEnrollmentEndDate()) {
            return false;
        }
        return getEnrollmentEndDate().isBefore(date);
    }

    void closeRecruiting() {
        recruitStatus = RecruitStatus.CLOSE;
    }

    boolean isNeedToCloseRecruiting(final LocalDate now) {
        return getRecruitStatus().equals(RecruitStatus.OPEN) && getEnrollmentEndDate() != null
                && getEnrollmentEndDate().isBefore(now);
    }

    public RecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    public LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public boolean isCloseEnrollment() {
        return recruitStatus.equals(RecruitStatus.CLOSE);
    }
}
