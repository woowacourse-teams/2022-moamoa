package com.woowacourse.moamoa.review.service.response;

import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private MemberResponse member;
    private String createdDate;
    private String lastModifiedDate;
    private String content;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.member = MemberResponse.from(review.getMember());
        this.createdDate = review.getCreatedDate().toLocalDate().toString();
        this.lastModifiedDate = review.getLastModifiedDate().toLocalDate().toString();
        this.content = review.getContent();
    }
}
