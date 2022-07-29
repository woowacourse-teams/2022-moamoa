package com.woowacourse.moamoa.review.service.response;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.Review;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class ReviewResponse {

    private Long id;
    private WriterResponse member;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private String content;

    public ReviewResponse(final ReviewData reviewData) {
        this(reviewData.getId(), new WriterResponse(reviewData.getMember()), reviewData.getCreatedDate(),
                reviewData.getLastModifiedDate(), reviewData.getContent());
    }
}
