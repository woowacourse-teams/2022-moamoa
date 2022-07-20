package com.woowacourse.moamoa.study.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.tag.service.response.TagResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyDetailResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;
    private String description;
    @JsonProperty(value = "current_member_count")
    private Integer currentMemberCount;
    @JsonProperty(value = "max_member_count")
    private Integer maxMemberCount;
    private String deadline;
    @JsonProperty(value = "start_date")
    private String startDate;
    @JsonProperty(value = "end_date")
    private String endDate;
    private String owner;
    private List<MemberResponse> members;
    private List<TagResponse> tags;
}
