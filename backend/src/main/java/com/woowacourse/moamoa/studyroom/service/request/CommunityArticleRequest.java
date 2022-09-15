package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.CommunityContent;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class CommunityArticleRequest implements ArticleRequest<CommunityContent> {

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 30)
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 5000)
    private String content;

    public CommunityArticleRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public CommunityContent createContent() {
        return new CommunityContent(title, content);
    }
}
