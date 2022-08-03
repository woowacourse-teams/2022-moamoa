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
public class StudyOwnerAndTagsData {

    private MemberData owner;
    private List<TagSummaryData> tags;

    public void addTag(TagSummaryData tagSummaryData) {
        this.tags.add(tagSummaryData);
    }
}
