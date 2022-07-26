package com.woowacourse.moamoa.study.query.data;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyStudyData {

    private Long id;
    private String title;
    private String studyStatus;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private String startDate;
    private String endDate;
    private MemberData owner;
    private List<TagSummaryData> tags;
}
