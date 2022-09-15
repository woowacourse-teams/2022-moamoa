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
    private Integer maxMemberCount;

    @Enumerated(STRING)
    @Column(name = "recruitment_status")
    private RecruitStatus recruitStatus;

    private LocalDate enrollmentEndDate;

    public RecruitPlanner(final Integer maxMemberCount, final RecruitStatus recruitStatus, final LocalDate enrollmentEndDate) {
        this.maxMemberCount = maxMemberCount;
        this.recruitStatus = recruitStatus;
        this.enrollmentEndDate = enrollmentEndDate;
    }

    boolean isRecruitedBeforeThan(LocalDate date) {
        if (!hasEnrollmentEndDate()) {
            return false;
        }
        return enrollmentEndDate.isBefore(date);
    }

    boolean hasEnrollmentEndDate() {
        return enrollmentEndDate != null;
    }

    void updateRecruiting(final LocalDate now) {
        if (isNeedToCloseRecruiting(now)) {
            closeRecruiting();
        }
    }

    private boolean isNeedToCloseRecruiting(final LocalDate now) {
        return recruitStatus.equals(RECRUITMENT_START) && isRecruitedBeforeThan(now);
    }

    void closeRecruiting() {
        recruitStatus = RECRUITMENT_END;
    }

    void startRecruiting() {
        recruitStatus = RECRUITMENT_START;
    }

    boolean isCloseEnrollment() {
        return recruitStatus.equals(RECRUITMENT_END);
    }

    int getCapacity() {
        if (hasCapacity()) {
            return maxMemberCount;
        }
        throw new IllegalStateException("최대 인원 정보가 없습니다.");
    }

    boolean hasCapacity() {
        return maxMemberCount != null;
    }

    public LocalDate getEnrollmentEndDate() {
        return enrollmentEndDate;
    }
}
