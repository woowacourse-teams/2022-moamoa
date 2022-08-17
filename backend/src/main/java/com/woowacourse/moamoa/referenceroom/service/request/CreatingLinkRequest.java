package com.woowacourse.moamoa.referenceroom.service.request;

import com.woowacourse.moamoa.referenceroom.domain.Author;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.review.domain.AssociatedStudy;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatingLinkRequest {

    @NotBlank(message = "공유할 링크 URL을 입력해 주세요.")
    @Pattern(
            regexp = "^((http(s?))\\:\\/\\/)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$",
            message = "Link 형식이 유효하지 않습니다."
    )
    @Size(max = 500, message = "링크 URL은 500자를 초과할 수 없습니다.")
    private String linkUrl;

    @Size(max = 40, message = "설명은 40자를 초과할 수 없습니다.")
    private String description;

    public Link toLink(final Long studyId, final Long memberId) {
        return new Link(new AssociatedStudy(studyId), new Author(memberId), linkUrl, description);
    }
}
