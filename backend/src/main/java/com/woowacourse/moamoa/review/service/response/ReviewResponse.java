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
    private String createdDate;
    private String lastModifiedDate;
    private String content;

    public ReviewResponse(final ReviewData reviewData) {
        this(reviewData.getId(), new WriterResponse(reviewData.getMember()), reviewData.getCreatedDate(),
                reviewData.getLastModifiedDate(), reviewData.getContent());
    }

    public ReviewResponse(final Long id, final WriterResponse member, final LocalDate createdDate,
                          final LocalDate lastModifiedDate, final String content) {
        this.id = id;
        this.member = member;
        this.createdDate = createdDate.toString();
        this.lastModifiedDate = lastModifiedDate.toString();
        this.content = content;
    }
}
