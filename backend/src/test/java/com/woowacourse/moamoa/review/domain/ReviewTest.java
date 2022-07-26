package com.woowacourse.moamoa.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @DisplayName("스터디 시작 전에 후기를 작성하면 400 에러를 반환한다.")
    @Test
    void writeable() {
        // given
        final Details details = new Details("title", "excerpt", "thumbnail", "status", "description");
        final Participants participants = new Participants(1, 5, List.of(), 1L);
        final Period period = new Period(null, LocalDate.now().plusDays(10), null);
        final AttachedTags attachedTags = new AttachedTags(List.of());
        final Study study = new Study(details, participants, period, attachedTags);

        // when
        final AssociatedStudy associatedStudy = new AssociatedStudy(1L);
        final Member member = new Member(1L, "username", "imageUrl", "profileUrl");
        final Review review = new Review(null, associatedStudy, member, "content");

        // then
        assertThatThrownBy(() -> review.writeable(LocalDateTime.of(study.getPeriod().getStartDate(), LocalTime.MIDNIGHT)));
    }
}