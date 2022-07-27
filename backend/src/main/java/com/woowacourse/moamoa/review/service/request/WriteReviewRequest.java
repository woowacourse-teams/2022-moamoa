package com.woowacourse.moamoa.review.service.request;

import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import com.woowacourse.moamoa.review.domain.Review;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WriteReviewRequest {

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    public Review createByStudyAndMember(final AssociatedStudy associatedStudy, final Member member) {
        return new Review(null, associatedStudy, member, content);
    }
}
