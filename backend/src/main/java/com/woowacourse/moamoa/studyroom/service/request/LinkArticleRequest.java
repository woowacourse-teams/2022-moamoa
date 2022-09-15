package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.LinkContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LinkArticleRequest implements ArticleRequest<LinkContent> {

    @NotBlank(message = "공유할 링크 URL을 입력해 주세요.")
    @Size(max = 500, message = "링크 URL은 500자를 초과할 수 없습니다.")
    private String linkUrl;

    @Size(max = 40, message = "설명은 40자를 초과할 수 없습니다.")
    private String description;

    @Override
    public LinkContent createContent() {
        return new LinkContent(linkUrl, description);
    }
}
