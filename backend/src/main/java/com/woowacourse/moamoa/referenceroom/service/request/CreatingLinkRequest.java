package com.woowacourse.moamoa.referenceroom.service.request;

import com.woowacourse.moamoa.referenceroom.domain.Link;
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

    public Link toLink(final Long studyId, final Long memberId) {
        return new Link(studyId, memberId, linkUrl, description);
    }
}
