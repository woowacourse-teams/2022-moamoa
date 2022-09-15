package com.woowacourse.moamoa.studyroom.service.request;

import com.woowacourse.moamoa.studyroom.domain.article.NoticeContent;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class NoticeArticleRequest implements ArticleRequest<NoticeContent> {

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 30)
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Length(max = 5000)
    private String content;

    public NoticeArticleRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public NoticeContent createContent() {
        return new NoticeContent(title, content);
    }
}
