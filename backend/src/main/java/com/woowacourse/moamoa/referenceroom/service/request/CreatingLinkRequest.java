package com.woowacourse.moamoa.referenceroom.service.request;

import com.woowacourse.moamoa.referenceroom.domain.Author;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatingLinkRequest {

    @NotBlank(message = "공유할 링크 URL을 입력해 주세요.")
    private String linkUrl;

    @Size(max = 50, message = "50글자를 초과할 수 없습니다.")
    private String description;

    public Link toLink(final AssociatedStudy associatedStudy, final Author author) {
        return new Link(associatedStudy, author, linkUrl, description);
    }
}
