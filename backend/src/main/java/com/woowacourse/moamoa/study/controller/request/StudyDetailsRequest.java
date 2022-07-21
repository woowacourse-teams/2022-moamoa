package com.woowacourse.moamoa.study.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudyDetailsRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String excerpt;

    @NotBlank
    private String thumbnail;

    @NotBlank
    private String description;

    @Min(1)
    private Integer maxMemberCount;
}
