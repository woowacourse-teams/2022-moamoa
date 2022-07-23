package com.woowacourse.moamoa.review.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private MemberData member;
    private String createdDate;
    private String lastModifiedDate;
    private String content;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.member = new MemberData(review.getMember().getGithubId(), review.getMember().getUsername(),
                review.getMember().getImageUrl(), review.getMember().getProfileUrl());
        this.createdDate = review.getCreatedDate().toLocalDate().toString();
        this.lastModifiedDate = review.getLastModifiedDate().toLocalDate().toString();
        this.content = review.getContent();
    }
}
