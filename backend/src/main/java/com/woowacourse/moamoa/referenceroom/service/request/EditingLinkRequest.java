package com.woowacourse.moamoa.referenceroom.service.request;

import com.woowacourse.moamoa.referenceroom.domain.Author;
import com.woowacourse.moamoa.referenceroom.domain.Link;
import com.woowacourse.moamoa.studyroom.domain.review.AssociatedStudy;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EditingLinkRequest {

    @NotBlank(message = "공유할 링크 URL을 입력해 주세요.")
    @Size(max = 500, message = "링크 URL은 500자를 초과할 수 없습니다.")
    private String linkUrl;

    @Size(max = 40, message = "설명은 40자를 초과할 수 없습니다.")
    private String description;

    public Link toLink(final Long studyId, final Long memberId) {
        return new Link(new AssociatedStudy(studyId), new Author(memberId), linkUrl, description);
    }
}
