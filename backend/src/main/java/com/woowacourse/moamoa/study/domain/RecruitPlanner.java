package com.woowacourse.moamoa.study.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class RecruitPlanner {

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_status")
    private RecruitStatus recruitStatus;

    private LocalDate enrollmentEndDate;

    protected RecruitPlanner() {
    }

    public RecruitPlanner(final RecruitStatus recruitStatus, final LocalDate enrollmentEndDate) {
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

    void closeRecruiting() {
        recruitStatus = RecruitStatus.CLOSE;
    }

    boolean isNeedToCloseRecruiting(final LocalDate now) {
        return recruitStatus.equals(RecruitStatus.OPEN) && isRecruitedBeforeThan(now);
    }

    public LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public boolean isCloseEnrollment() {
        return recruitStatus.equals(RecruitStatus.CLOSE);
    }
}
