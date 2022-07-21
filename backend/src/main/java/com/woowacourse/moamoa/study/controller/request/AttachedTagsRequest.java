package com.woowacourse.moamoa.study.controller.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AttachedTagsRequest {

    private List<Long> tagIds;
}
