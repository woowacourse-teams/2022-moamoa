package com.woowacourse.moamoa.community.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ArticleData {

    private final Long id;
    private final MemberData memberData;
    private final String title;
    private final String content;
    private final LocalDate createdDate;
    private final LocalDate lastModifiedDate;

    public ArticleData(final Long id, final MemberData memberData, final String title, final String content,
                       final LocalDate createdDate,
                       final LocalDate lastModifiedDate) {
        this.id = id;
        this.memberData = memberData;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
