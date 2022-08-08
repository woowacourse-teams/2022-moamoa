package com.woowacourse.moamoa.studyroom.linksharingroom.service.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatingLinkRequest {

    @NotBlank(message = "공유할 링크 URL을 입력해 주세요.")
    private String linkUrl;

    private String description;
}
