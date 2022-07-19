package com.woowacourse.moamoa.study.domain.studytag;

import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudySearchCondition {

    private String title;
    private List<Tag> tags;
}
